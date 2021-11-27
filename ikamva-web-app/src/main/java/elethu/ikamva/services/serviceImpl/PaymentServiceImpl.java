package elethu.ikamva.services.serviceImpl;

import elethu.ikamva.commons.DateFormatter;
import elethu.ikamva.domain.Member;
import elethu.ikamva.domain.Payment;
import elethu.ikamva.domain.TransactionType;
import elethu.ikamva.exception.PaymentException;
import elethu.ikamva.repositories.PaymentRepository;
import elethu.ikamva.services.MemberService;
import elethu.ikamva.services.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class PaymentServiceImpl implements PaymentService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentServiceImpl.class);

    private final PaymentRepository paymentRepository;
    private final DateFormatter dateFormatter;
    private final MemberService memberService;

    @Override
    public Payment SavePayment(Payment payment) {
        if (!IsPaymentActive(payment.getAmount(), payment.getInvestmentId(), dateFormatter.returnLocalDate(payment.getPaymentDate().toString()))) {
            Member member = memberService.FindMemberByInvestmentId(payment.getInvestmentId());
            if (Objects.nonNull(member) && !IsPaymentActive(payment.getAmount(), payment.getInvestmentId(), payment.getPaymentDate())) {
                payment.setMemberPayments(member);
                payment.setTransactionType(getTransationType(payment.getAmount()));
            } else {
                throw new PaymentException(HttpStatus.INTERNAL_SERVER_ERROR.value() + ", could not add new payment for: " + payment.getInvestmentId());
            }

            return paymentRepository.save(payment);
        } else
            throw new PaymentException(HttpStatus.INTERNAL_SERVER_ERROR.value() + ", there is already a a similar payment on the same day fot the same member: " + payment.getInvestmentId());
    }

    @Override
    public void BulkSavePayments(List<Payment> payments) {
        payments.forEach(payment -> {
            boolean checkPayment = IsPaymentActive(payment.getAmount(), payment.getInvestmentId(), payment.getPaymentDate());
            Member member = memberService.FindMemberByInvestmentId(payment.getInvestmentId());
            if (Objects.nonNull(member) && !checkPayment) {
                LOGGER.info("Saving payment for investment id: {} made on {} for amount of {}", payment.getInvestmentId(), payment.getPaymentDate(), payment.getAmount());
                payment.setMemberPayments(member);
                payment.setPaymentDate(dateFormatter.returnLocalDate(payment.getPaymentDate().toString()));
                payment.setTransactionType(getTransationType(payment.getAmount()));

                paymentRepository.save(payment);
            } else {
                LOGGER.error("Payment for investment id: {} with amount: {} on date: {} already exists.", payment.getInvestmentId(), payment.getAmount(), payment.getPaymentDate());
            }
        });
    }

    @Override
    public Payment UpdatePayment(Long paymentId, Payment payment) {

        Payment updatePayment = paymentRepository.findPaymentById(paymentId).orElseThrow(() ->
                new PaymentException(String.format("member with id: %d does not exist cannot update payment", paymentId)));
        updatePayment.setPaymentDate(payment.getPaymentDate());
        updatePayment.setTransactionType(getTransationType(payment.getAmount()));
        updatePayment.setPaymentReference(payment.getPaymentReference());
        updatePayment.setInvestmentId(payment.getInvestmentId());
        updatePayment.setAmount(payment.getAmount());
        return paymentRepository.save(updatePayment);
    }

    @Override
    public Payment DeletePayment(Long id) {
        Optional<Payment> paymentOptional = paymentRepository.findById(id);
        Payment pay = paymentOptional.orElseThrow(() -> new PaymentException(HttpStatus.INTERNAL_SERVER_ERROR.value() + ", Could not find payment for with id: " + id));
        pay.setEndDate(dateFormatter.returnLocalDate());

        return paymentRepository.save(pay);
    }

    @Override
    public Payment FindPaymentById(Long id) {
        Optional<Payment> paymentOptional = paymentRepository.findPaymentById(id);
        return paymentOptional.orElseThrow(() -> new PaymentException(HttpStatus.INTERNAL_SERVER_ERROR.value() + ", Could not find payment for payment id: " + id));
    }

    @Override
    public List<Payment> FindPaymentByInvestId(String investmentId) {
        List<Payment> paymentList = paymentRepository.findPaymentByInvestmentId(investmentId);

        if (paymentList.isEmpty()) {
            throw new PaymentException(HttpStatus.INTERNAL_SERVER_ERROR.value() + ", This member: " + investmentId + " has no payments made. ");
        }
        return paymentList;
    }

    @Override
    public List<Payment> FindPaymentsBetweenDates(LocalDate fromDate, LocalDate toDate) {
        List<Payment> memberPayments = paymentRepository.findPaymentsBetween(dateFormatter.returnLocalDate(fromDate.toString()),
                dateFormatter.returnLocalDate(toDate.toString()));

        if (memberPayments.isEmpty()) {
            throw new PaymentException(HttpStatus.INTERNAL_SERVER_ERROR.value() + ", No payments were found between: " + fromDate + " and " + toDate);
        }

        return memberPayments;
    }

    @Override
    public List<Payment> FindMemberPaymentsBetweenDates(String memberInvestId, LocalDate fromDate, LocalDate toDate) {
        List<Payment> memberPayments = paymentRepository.findPaymentsByDateRangeForMember(memberInvestId, fromDate, toDate);

        if (memberPayments.isEmpty()) {
            throw new PaymentException(HttpStatus.INTERNAL_SERVER_ERROR.value() + ", No payments were found between: " + fromDate + " and " + toDate);
        }

        return memberPayments;
    }

    @Override
    public boolean IsPaymentActive(double paymentAmount, String investmentID, LocalDate paymentDate) {
        return paymentRepository.checkPayment(paymentAmount, investmentID, paymentDate).isPresent();
    }

    public TransactionType getTransationType(double amount) {
        TransactionType transactionType;
        if (amount > 0) {
            transactionType = TransactionType.MONTHLY_CONTRIBUTION;
        } else {
            transactionType = TransactionType.BANK_CHARGES;
        }
        return transactionType;
    }
}
