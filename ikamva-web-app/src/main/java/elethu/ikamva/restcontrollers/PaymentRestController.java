package elethu.ikamva.restcontrollers;

import elethu.ikamva.domain.Payment;
import elethu.ikamva.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

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
        Payment newPayment = paymentService.savePayment(payment);
        return new ResponseEntity<>(newPayment, HttpStatus.CREATED);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("search-payments/{fromDate}/{toDate}")
    List<Payment> findPaymentsBetweenDates(@PathVariable(value = "fromDate")
                                           @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
                                           @PathVariable(value = "toDate")
                                           @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate){
        return paymentService.findPaymentsBetweenDates(fromDate, toDate);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/search/{memberInvestId}/{fromDate}/{toDate}")
    List<Payment> findMemberPaymentsBetweeDates(@RequestParam(value = "memberInvestId") String memberInvestId,
                                                @RequestParam(value = "fromDate")
                                                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
                                                @RequestParam(value = "toDate")
                                                @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate toDate){
        return paymentService.findMemberPaymentsBetweenDates(memberInvestId, fromDate, toDate);
    }

    @GetMapping("/invest/{investmentId}")
    List<Payment> getPaymentByInvestId(@PathVariable String investmentId){
        return paymentService.findPaymentByInvestId(investmentId);
    }

    @GetMapping("/{id}")
    Payment getPaymentById(@PathVariable Long id){
        return paymentService.findPaymentById(id);
    }
}
