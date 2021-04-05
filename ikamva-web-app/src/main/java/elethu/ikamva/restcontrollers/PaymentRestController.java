package elethu.ikamva.restcontrollers;

import elethu.ikamva.domain.Payment;
import elethu.ikamva.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentRestController {

    private final PaymentService paymentService;

    @Autowired
    public PaymentRestController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/add")
    ResponseEntity<Payment> addPayment(@RequestBody Payment payment){
        Payment newPayment = paymentService.saveOrUpdatePayment(payment);
        return new ResponseEntity<>(newPayment, HttpStatus.CREATED);
    }

    @GetMapping("/")
    List<Payment> getAllPayments(@RequestParam LocalDate fromDate, @RequestParam LocalDate toDate, @RequestParam String memberInvestId){
        return paymentService.FindAllPaymentsBetween(fromDate, fromDate, memberInvestId);
    }

    @GetMapping("/invest/{investmentId}")
    List<Payment> getPaymentByInvestId(@PathVariable String investmentId){
        return paymentService.FindPaymentByInvestId(investmentId);
    }

    @GetMapping("/{id}")
    Payment getPaymentById(@PathVariable Long id){
        return paymentService.FindPaymentById(id);
    }
}
