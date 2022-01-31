package elethu.ikamva.restcontrollers;

import elethu.ikamva.domain.Payment;
import elethu.ikamva.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
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
    ResponseEntity<Payment> addPayment(@RequestBody Payment payment) {
        Payment newPayment = paymentService.savePayment(payment);
        return new ResponseEntity<>(newPayment, HttpStatus.CREATED);
    }

    @PostMapping("/save-payments")
    ResponseEntity<String> saveAllPayments(@RequestBody List<Payment> memberPayments) {
        paymentService.bulkSavePayments(memberPayments);
        return new ResponseEntity<>("Successfully saved all payments", HttpStatus.CREATED);
    }

    @PutMapping("/update")
    ResponseEntity<Payment> updatePayment(@RequestBody Payment payment) {
        Payment updatePayment = paymentService.updatePayment(payment);
        return new ResponseEntity<>(updatePayment, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("search-payments/{fromDate}/{toDate}")
    List<Payment> findPaymentsBetweenDates(@PathVariable(value = "fromDate")
                                           @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
                                           @PathVariable(value = "toDate")
                                           @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate) {
        return paymentService.findPaymentsBetweenDates(fromDate, toDate);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/search/{memberInvestId}/{fromDate}/{toDate}")
    List<Payment> findMemberPaymentsBetweenDates(@PathVariable String memberInvestId,
                                                @PathVariable(value = "fromDate")
                                                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
                                                @PathVariable(value = "toDate")
                                                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate) {
        return paymentService.findMemberPaymentsBetweenDates(memberInvestId, fromDate, toDate);
    }

    @GetMapping("/invest/{investmentId}")
    List<Payment> getPaymentByInvestId(@PathVariable String investmentId) {
        return paymentService.findPaymentByInvestId(investmentId);
    }

    @GetMapping("/{id}")
    Payment getPaymentById(@PathVariable Long id) {
        return paymentService.findPaymentById(id);
    }

    @PostMapping("/upload")
    void uploadStatement(@RequestParam("file") MultipartFile file) throws FileNotFoundException {
        paymentService.processCSVFile(file);
    }
}
