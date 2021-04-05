package elethu.ikamva.services.serviceImpl;

import elethu.ikamva.domain.Member;
import elethu.ikamva.domain.Payment;
import elethu.ikamva.domain.TransactionType;
import elethu.ikamva.exception.PaymentException;
import elethu.ikamva.repositories.PaymentRepository;
import elethu.ikamva.services.MemberService;
import elethu.ikamva.services.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;


    private final MemberService memberService;

    public PaymentServiceImpl(PaymentRepository paymentRepository, MemberService memberService) {
        this.paymentRepository = paymentRepository;
        this.memberService = memberService;
    }

    @Override
    public Payment saveOrUpdatePayment(Payment payment) {
        //System.out.println(payment.toString());
        Member member = memberService.FindMemberByInvestmentId(payment.getInvestmentId());
        if (member != null){
            System.out.println("Member is found");
//            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy:HH:mm:ss");
//            OffsetDateTime offsetDateTime = OffsetDateTime.parse(payment.getDateOfPayment() + ":00:00:00",dateTimeFormatter);
//            payment.setPaymentDate(offsetDateTime);

            payment.setMemberPayments(member);
            System.out.println("now setting transaction");
            payment.setTransactionType(getTransationType(payment.getAmount()));
            System.out.println("Done setting transaction");
        }else{
            throw new PaymentException(HttpStatus.INTERNAL_SERVER_ERROR.value() + ", could not add new payment for: " + payment.getInvestmentId());
        }

        //System.out.println(payment.toString());
        return  paymentRepository.save(payment);
    }

    @Override
    public void DeletePayment(Long id) {

        Payment pay = FindPaymentById(id);

        if (pay != null){
            saveOrUpdatePayment(pay);
        }else
            throw new PaymentException(HttpStatus.INTERNAL_SERVER_ERROR.value() + ", Could not find payment for with id: " + id);
    }

    @Override
    public Payment FindPaymentById(Long id) {
        Optional<Payment> paymentOptional = paymentRepository.findById(id);

        if (paymentOptional.isEmpty()) {
            throw new PaymentException(HttpStatus.INTERNAL_SERVER_ERROR.value() +", Could not find payment for payment id: " + id);
        }
        return paymentOptional.get();
    }

    @Override
    public List<Payment> FindPaymentByInvestId(String investmentId) {
        List<Payment> paymentList = paymentRepository.FindPaymentByInvestmentId(investmentId);

        if (paymentList.isEmpty()) {
            throw new PaymentException(HttpStatus.INTERNAL_SERVER_ERROR.value() + ", This member: " + investmentId + " has no payments made. ");
        }
       return paymentList;
    }

    @Override
    public List<Payment> FindAllPaymentsBetween(LocalDate fromDate, LocalDate toDate, String memberInvestId) {
        List<Payment> payments = paymentRepository.FindPaymentsBetween(fromDate, toDate);

        if(payments.isEmpty()){
            throw new PaymentException(HttpStatus.INTERNAL_SERVER_ERROR.value() + ", No payments were found between: " + fromDate + " and " + toDate);
        }

        return payments;
    }

    @Override
    public Boolean IsPaymentActive(double payment, String investmentID, LocalDateTime paymentDate) {
        return paymentRepository.CheckPayment(payment, investmentID, paymentDate);
    }

    public TransactionType getTransationType(double amount){
        System.out.println("Working out the Transaction Type");
        TransactionType transactionType;
        if(amount > 0){
            transactionType = TransactionType.MONTHLY_CONTRIBUTION;
        }else{
            transactionType = TransactionType.BANK_CHARGES;
        }
        return transactionType;
    }
}
