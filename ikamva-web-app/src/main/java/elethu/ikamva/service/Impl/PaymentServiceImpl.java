package elethu.ikamva.service.Impl;

import elethu.ikamva.aspects.ExecutionTime;
import elethu.ikamva.commons.DateFormatter;
import elethu.ikamva.domain.Member;
import elethu.ikamva.domain.Payment;
import elethu.ikamva.domain.enums.TransactionType;
import elethu.ikamva.exception.MemberException;
import elethu.ikamva.exception.PaymentException;
import elethu.ikamva.repositories.PaymentRepository;
import elethu.ikamva.service.MemberService;
import elethu.ikamva.service.PaymentService;
import elethu.ikamva.utils.CSVPaymentProcessor;
import elethu.ikamva.view.PaymentView;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentServiceImpl.class);
    private final PaymentRepository paymentRepository;
    private final MemberService memberService;

    @Override
    @ExecutionTime
    public Payment savePayment(Payment payment) {
        if (isPaymentActive(payment.getAmount(), payment.getInvestmentId(), payment.getPaymentDate())) {
            throw new PaymentException(HttpStatus.INTERNAL_SERVER_ERROR.value() + ", there is already a a similar payment on the same day fot the same member: " + payment.getInvestmentId());
        }

        Member member = Optional.of(memberService.findMemberByInvestmentId(payment.getInvestmentId()))
                .orElseThrow(() ->
                        new PaymentException(HttpStatus.INTERNAL_SERVER_ERROR.value() + ", could not add new payment for:: " + payment.getInvestmentId()));

        payment.setMemberPayments(member);
        payment.setTransactionType(getTransactionType(payment.getAmount()));
        payment.setCreatedDate(DateFormatter.returnLocalDateTime());

        return paymentRepository.save(payment);
    }

    @Override
    @ExecutionTime
    public void bulkSavePayments(List<Payment> payments) {
        AtomicInteger successCounter = new AtomicInteger(0);
        AtomicInteger failedCounter = new AtomicInteger(0);
        payments.forEach(payment -> {
            var paymentExists = isPaymentActive(payment.getAmount(), payment.getInvestmentId(), payment.getPaymentDate());
            try {
                Member member = memberService.findMemberByInvestmentId(payment.getInvestmentId());
                if (paymentExists) {
                    LOGGER.error("Payment for investment id: {} with amount: {} on date: {} already exists.", payment.getInvestmentId()
                            , payment.getAmount(), payment.getPaymentDate());
                } else {
                    payment.setMemberPayments(member);
                    payment.setCreatedDate(DateFormatter.returnLocalDateTime());
                    payment.setTransactionType(getTransactionType(payment.getAmount()));
                    paymentRepository.save(payment);
                    successCounter.getAndIncrement();
                }
            } catch (MemberException e) {
                failedCounter.getAndIncrement();
                LOGGER.error("Did not find member with investment id: {}", payment.getInvestmentId());
            }
        });
        LOGGER.info("Total number of successul payments made: {}", successCounter);
        LOGGER.info("Total number of unsuccessful payments is: {}", failedCounter);
    }

    @Override
    @ExecutionTime
    public Payment updatePayment(Payment payment) {
        paymentRepository.findPaymentById(payment.getId()).orElseThrow(() ->
                new PaymentException(String.format("Payment with id: %d does not exist, cannot update payment", payment.getId())));

        return paymentRepository.save(payment);
    }

    @Override
    @ExecutionTime
    public Payment deletePayment(Long id) {
        var pay = paymentRepository.findById(id)
                .orElseThrow(() -> new PaymentException(HttpStatus.INTERNAL_SERVER_ERROR.value() + ", Could not find payment for with id: " + id));
        pay.setEndDate(DateFormatter.returnLocalDate());

        return paymentRepository.save(pay);
    }

    @Override
    @ExecutionTime
    public Payment findPaymentById(Long id) {
        return paymentRepository.findPaymentById(id)
                .orElseThrow(() -> new PaymentException(HttpStatus.INTERNAL_SERVER_ERROR.value() + ", Could not find payment for payment id: " + id));
    }

    @Override
    @ExecutionTime
    public PaymentView findPaymentByInvestId(String investmentId, int pageNo, int pageSize, String sortBy) {
        var paging = PageRequest.of(pageNo - 1, pageSize, Sort.by(sortBy));
        var memberPaymentList = paymentRepository.findPaymentByInvestmentId(investmentId, paging);

        return paymentView(memberPaymentList);
    }

    @Override
    @ExecutionTime
    public PaymentView findPaymentsBetweenDates(LocalDate fromDate, LocalDate toDate, int pageNo, int pageSize, String sortBy) {
        var pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        var memberPayments = paymentRepository.findPaymentsBetween(DateFormatter.returnLocalDate(fromDate.toString()),
                DateFormatter.returnLocalDate(toDate.toString()), pageable);

        return paymentView(memberPayments);
    }

    @Override
    @ExecutionTime
    public PaymentView findMemberPaymentsBetweenDates(String memberInvestId, LocalDate fromDate, LocalDate toDate, int pageNo, int pageSize, String sortBy) {
        var pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        var memberPayments = paymentRepository.findPaymentsByDateRangeForMember(memberInvestId, fromDate, toDate, pageable);

        return paymentView(memberPayments);
    }

    @Override
    @ExecutionTime
    public boolean isPaymentActive(double paymentAmount, String investmentID, LocalDate paymentDate) {
        return paymentRepository.checkPayment(paymentAmount, investmentID, paymentDate).isPresent();
    }

    @Override
    @ExecutionTime
    public void processCSVFile(MultipartFile csvFile) {
        LOGGER.info("ServiceInvocation::ProcessCSVFile");
        try {
            if (!CSVPaymentProcessor.isCSVFormat(csvFile) || csvFile.isEmpty()) {
                LOGGER.error("Error with a file type");
                throw new FileNotFoundException("The file uploaded is not a CSV file, please correct and upload again");
            }

            var csvBulkPayments = CSVPaymentProcessor.bulkCSVFileProcessing(csvFile.getInputStream());
            LOGGER.info("Total csv records: {}", csvBulkPayments.size());
            this.bulkSavePayments(csvBulkPayments);
        } catch (IOException e) {
            LOGGER.info("There was a problem with the usre of the CSV processor");
            throw new PaymentException("There was a problem processing uploaded file, please make sure its a csv file.");
        }
    }

    private TransactionType getTransactionType(double amount) {
        return amount > 0
                ? TransactionType.MONTHLY_CONTRIBUTION
                : TransactionType.BANK_CHARGES;
    }

    private PaymentView paymentView(Page<Payment> payments) {
        PaymentView paymentView = new PaymentView();

        paymentView.setPayments(payments.getContent());
        paymentView.setPage(String.format("%s of %s", payments.getNumber() + 1, payments.getTotalPages()));
        paymentView.setSize(payments.getSize());
        var pageTotal = payments.getContent()
                .stream()
                .filter(payment -> payment.getTransactionType().equals(TransactionType.MONTHLY_CONTRIBUTION))
                .map(Payment::getAmount)
                .reduce(0.0, Double::sum);

        paymentView.setTotal(pageTotal);

        return paymentView;
    }

    private List<Payment> returnPayments(Page<Payment> payments) {
        return payments.hasContent()
                ? payments.getContent()
                : Collections.emptyList();
    }
}
