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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@RequiredArgsConstructor
@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final DateFormatter dateFormatter;
    private final MemberService memberService;

    @Override
    public Payment savePayment(Payment payment) {
        if(!IsPaymentActive(payment.getAmount(), payment.getInvestmentId(), dateFormatter.returnLocalDate(payment.getPaymentDate().toString()))) {
            Member member = memberService.FindMemberByInvestmentId(payment.getInvestmentId());
            if (member != null){
//          // payment.setPaymentDate(dateFormatter.returnLocalDate(payment.getPaymentDate().toString()));
                payment.setMemberPayments(member);
                payment.setTransactionType(getTransationType(payment.getAmount()));
            }else{
                throw new PaymentException(HttpStatus.INTERNAL_SERVER_ERROR.value() + ", could not add new payment for: " + payment.getInvestmentId());
            }

            return  paymentRepository.save(payment);
        } else
            throw new PaymentException(HttpStatus.INTERNAL_SERVER_ERROR.value() + ", there is already a a similar payment on the same day fot the same member: " + payment.getInvestmentId());
    }

    @Override
    public Payment DeletePayment(Long id) {
        Optional<Payment> paymentOptional = paymentRepository.findById(id);
        Payment pay = paymentOptional.orElseThrow(() -> new PaymentException(HttpStatus.INTERNAL_SERVER_ERROR.value() + ", Could not find payment for with id: " + id));
        pay.setEndDate(dateFormatter.returnLocalDate());

        return paymentRepository.save(pay);
    }

    @Override
    public Payment findPaymentById(Long id) {
        Optional<Payment> paymentOptional = paymentRepository.findPaymentById(id);
        return paymentOptional.orElseThrow(() -> new PaymentException(HttpStatus.INTERNAL_SERVER_ERROR.value() +", Could not find payment for payment id: " + id));
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
        List<Payment> memberPayments = paymentRepository.findPaymentsBetween(dateFormatter.returnLocalDate(fromDate.toString()),
                dateFormatter.returnLocalDate(toDate.toString()));

        if(memberPayments.isEmpty()){
            throw new PaymentException(HttpStatus.INTERNAL_SERVER_ERROR.value() + ", No payments were found between: " + fromDate + " and " + toDate);
        }

        return memberPayments;
    }

    @Override
    public List<Payment> findMemberPaymentsBetweenDates(String memberInvestId, LocalDate fromDate, LocalDate toDate) {
        List<Payment> memberPayments = paymentRepository.findPaymentsByDateRangeForMember(memberInvestId, fromDate, toDate);

        if(memberPayments.isEmpty()){
            throw new PaymentException(HttpStatus.INTERNAL_SERVER_ERROR.value() + ", No payments were found between: " + fromDate + " and " + toDate);
        }

        return memberPayments;
    }

    @Override
    public boolean IsPaymentActive(double payment, String investmentID, LocalDate paymentDate) {
        return paymentRepository.checkPayment(payment, investmentID, paymentDate).isPresent();
    }

    public TransactionType getTransationType(double amount){
        TransactionType transactionType;
        if(amount > 0){
            transactionType = TransactionType.MONTHLY_CONTRIBUTION;
        }else{
            transactionType = TransactionType.BANK_CHARGES;
        }
        return transactionType;
    }
}
