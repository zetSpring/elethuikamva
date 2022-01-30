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
    ResponseEntity<Payment> AddPayment(@RequestBody Payment payment) {
        Payment newPayment = paymentService.savePayment(payment);
        return new ResponseEntity<>(newPayment, HttpStatus.CREATED);
    }

    @PostMapping("/savePayments")
    ResponseEntity<String> SaveAllPayments(@RequestBody List<Payment> memberPayments) {
        paymentService.bulkSavePayments(memberPayments);
        return new ResponseEntity<>("Successfully stored all payments", HttpStatus.CREATED);
    }

    @PutMapping("/updatePayment/{id}")
    ResponseEntity<Payment> UpdatePayment(@PathVariable Long id, @RequestBody Payment payment) {
        Payment updatePayment = paymentService.updatePayment(id, payment);
        return new ResponseEntity<>(updatePayment, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("search-payments/{fromDate}/{toDate}")
    List<Payment> FindPaymentsBetweenDates(@PathVariable(value = "fromDate")
                                           @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
                                           @PathVariable(value = "toDate")
                                           @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate) {
        return paymentService.findPaymentsBetweenDates(fromDate, toDate);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/search/{memberInvestId}/{fromDate}/{toDate}")
    List<Payment> FindMemberPaymentsBetweeDates(@RequestParam(value = "memberInvestId") String memberInvestId,
                                                @RequestParam(value = "fromDate")
                                                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
                                                @RequestParam(value = "toDate")
                                                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate) {
        return paymentService.findMemberPaymentsBetweenDates(memberInvestId, fromDate, toDate);
    }

    @GetMapping("/invest/{investmentId}")
    List<Payment> GetPaymentByInvestId(@PathVariable String investmentId) {
        return paymentService.findPaymentByInvestId(investmentId);
    }

    @GetMapping("/{id}")
    Payment GetPaymentById(@PathVariable Long id) {
        return paymentService.findPaymentById(id);
    }

    @PostMapping("/upload")
    void UploadStatement(@RequestParam("file") MultipartFile file) throws FileNotFoundException {
        paymentService.processCSVFile(file);
    }
}
