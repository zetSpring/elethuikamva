package elethu.ikamva.services.serviceImpl;

import elethu.ikamva.commons.DateFormatter;
import elethu.ikamva.domain.Member;
import elethu.ikamva.domain.Payment;
import elethu.ikamva.domain.TransactionType;
import elethu.ikamva.exception.MemberException;
import elethu.ikamva.exception.PaymentException;
import elethu.ikamva.repositories.PaymentRepository;
import elethu.ikamva.services.MemberService;
import elethu.ikamva.services.PaymentService;
import elethu.ikamva.utils.CSVPaymentProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@RequiredArgsConstructor
@Service
public class PaymentServiceImpl implements PaymentService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentServiceImpl.class);
    private final PaymentRepository paymentRepository;
    private final MemberService memberService;

    @Override
    public Payment savePayment(Payment payment) {
        if (!isPaymentActive(payment.getAmount(), payment.getInvestmentId(), payment.getPaymentDate())) {
            Member member = memberService.findMemberByInvestmentId(payment.getInvestmentId());
            if (Objects.nonNull(member)) {
                payment.setMemberPayments(member);
                payment.setTransactionType(getTransactionType(payment.getAmount()));
                payment.setPaymentDate(DateFormatter.returnLocalDate(payment.getPaymentDate().toString()));
            } else {
                throw new PaymentException(HttpStatus.INTERNAL_SERVER_ERROR.value() + ", could not add new payment for: " + payment.getInvestmentId());
            }

            return paymentRepository.save(payment);
        } else
            throw new PaymentException(HttpStatus.INTERNAL_SERVER_ERROR.value() + ", there is already a a similar payment on the same day fot the same member: " + payment.getInvestmentId());
    }

    @Override
    public void bulkSavePayments(List<Payment> payments) {
        AtomicInteger successCounter = new AtomicInteger(0);
        AtomicInteger failedCounter = new AtomicInteger(0);
        payments.forEach(payment -> {
            boolean checkPayment = isPaymentActive(payment.getAmount(), payment.getInvestmentId(), payment.getPaymentDate());
            try {
                Member member = memberService.findMemberByInvestmentId(payment.getInvestmentId());
                if (!checkPayment) {
                    payment.setMemberPayments(member);
                    //payment.setPaymentDate(dateFormatter.returnLocalDate(payment.getPaymentDate().toString()));
                    payment.setTransactionType(getTransactionType(payment.getAmount()));
                    paymentRepository.save(payment);
                    successCounter.getAndIncrement();
                } else {
                    LOGGER.error("Payment for investment id: {} with amount: {} on date: {} already exists.", payment.getInvestmentId(), payment.getAmount(), payment.getPaymentDate());
                }
            } catch (MemberException e) {
                failedCounter.getAndIncrement();
                LOGGER.error("Did not find member with investment id: {}", payment.getInvestmentId());
                throw new MemberException(HttpStatus.INTERNAL_SERVER_ERROR.value() + ", there is already a a similar payment on the same day fot the same member: " + payment.getInvestmentId());
            }
        });
        LOGGER.info("Total number of successul payments made: {}", successCounter);
        LOGGER.info("Total number of unsuccessul payments is: {}", failedCounter);
    }

    @Override
    public Payment updatePayment(Payment payment) {
        paymentRepository.findPaymentById(payment.getId()).orElseThrow(() ->
                new PaymentException(String.format("Payment with id: %d does not exist, cannot update payment", payment.getId())));
        if(isPaymentActive(payment.getAmount(), payment.getInvestmentId(), payment.getPaymentDate())){
            return paymentRepository.save(payment);
        } else {
            throw new PaymentException(String.format("Payment of amount: %s, for member: %s, on the date: %s does not exist, cannot update", payment.getAmount(), payment.getInvestmentId(), payment.getPaymentDate()));
        }
    }

    @Override
    public Payment deletePayment(Long id) {
        Optional<Payment> paymentOptional = paymentRepository.findById(id);
        Payment pay = paymentOptional.orElseThrow(() -> new PaymentException(HttpStatus.INTERNAL_SERVER_ERROR.value() + ", Could not find payment for with id: " + id));
        pay.setEndDate(DateFormatter.returnLocalDate());

        return paymentRepository.save(pay);
    }

    @Override
    public Payment findPaymentById(Long id) {
        return paymentRepository.findPaymentById(id).orElseThrow(() -> new PaymentException(HttpStatus.INTERNAL_SERVER_ERROR.value() + ", Could not find payment for payment id: " + id));
    }

    @Override
    public List<Payment> findPaymentByInvestId(String investmentId) {
        List<Payment> paymentList = paymentRepository.findPaymentByInvestmentId(investmentId);

        if (paymentList.isEmpty()) {
            throw new PaymentException(HttpStatus.INTERNAL_SERVER_ERROR.value() + ", This member: " + investmentId + " has no payments made. ");
        }
        return paymentList;
    }

    @Override
    public List<Payment> findPaymentsBetweenDates(LocalDate fromDate, LocalDate toDate) {
        List<Payment> memberPayments = paymentRepository.findPaymentsBetween(DateFormatter.returnLocalDate(fromDate.toString()),
                DateFormatter.returnLocalDate(toDate.toString()));

        if (memberPayments.isEmpty()) {
            throw new PaymentException(HttpStatus.INTERNAL_SERVER_ERROR.value() + ", No payments were found between: " + fromDate + " and " + toDate);
        }

        return memberPayments;
    }

    @Override
    public List<Payment> findMemberPaymentsBetweenDates(String memberInvestId, LocalDate fromDate, LocalDate toDate) {
        List<Payment> memberPayments = paymentRepository.findPaymentsByDateRangeForMember(memberInvestId, fromDate, toDate);

        if (memberPayments.isEmpty()) {
            throw new PaymentException(HttpStatus.INTERNAL_SERVER_ERROR.value() + ", No payments were found between: " + fromDate + " and " + toDate);
        }

        return memberPayments;
    }

    @Override
    public boolean isPaymentActive(double paymentAmount, String investmentID, LocalDate paymentDate) {
        return paymentRepository.checkPayment(paymentAmount, investmentID, paymentDate).isPresent();
    }

    @Override
    public void processCSVFile(MultipartFile csvFile) {
        LOGGER.info("ServiceInvocation::ProcessCSVFile");
        try {
            if (CSVPaymentProcessor.isCSVFormat(csvFile)) {
                List<Payment> csvBulkPayments = CSVPaymentProcessor.BulkCSVFileProcessing(csvFile.getInputStream());
                LOGGER.info("Total csv records: {}", csvBulkPayments.size());
                bulkSavePayments(csvBulkPayments);
            } else {
                LOGGER.error("Error with a file type");
                throw new FileNotFoundException("The file uploaded is not a CSV file, please correct and upload again");
            }
        } catch (IOException e) {
            LOGGER.info("There was a problem with the usre of the CSV processor");
            throw new PaymentException("There was a problem processing uploaded file, please make sure its a csv file.");
        }
    }

    private TransactionType getTransactionType(double amount) {
        TransactionType transactionType = TransactionType.BANK_CHARGES;
        if (amount > 0) {
            transactionType = TransactionType.MONTHLY_CONTRIBUTION;
        }
        return transactionType;
    }
}
