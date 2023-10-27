package elethu.ikamva.controllers;

import elethu.ikamva.domain.Payment;
import elethu.ikamva.dto.PaymentDto;
import elethu.ikamva.mappers.PaymentMapper;
import elethu.ikamva.service.PaymentService;
import elethu.ikamva.view.PaymentView;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/payments")
@SecurityRequirement(name = "bearerAuth")
public class PaymentRestController {
    private final PaymentMapper mapper;
    private final PaymentService paymentService;

    @Autowired
    public PaymentRestController(PaymentService paymentService, PaymentMapper mapper) {
        this.paymentService = paymentService;
        this.mapper = mapper;
    }

    @PostMapping("/add")
    ResponseEntity<Payment> addPayment(@RequestBody PaymentDto dto) {
        Payment payment = mapper.dtoToModel(dto);
        Payment newPayment = paymentService.savePayment(payment);
        return new ResponseEntity<>(newPayment, HttpStatus.CREATED);
    }

    @PostMapping("/save-payments")
    ResponseEntity<String> saveAllPayments(@RequestBody List<PaymentDto> memberPayments) {
        List<Payment> payments = mapper.dtosToModels(memberPayments);
        paymentService.bulkSavePayments(payments);
        return new ResponseEntity<>("Successfully saved all payments", HttpStatus.CREATED);
    }

    @PutMapping("/update")
    ResponseEntity<Payment> updatePayment(@RequestBody PaymentDto paymentDto) {
        Payment payment = mapper.dtoToModel(paymentDto);
        Payment updatePayment = paymentService.updatePayment(payment);
        return new ResponseEntity<>(updatePayment, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("search-payments/{fromDate}/{toDate}")
    ResponseEntity<PaymentView> findPaymentsBetweenDates(
            @PathVariable(value = "fromDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
            @PathVariable(value = "toDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy) {

        PaymentView paymentsBetweenDates =
                paymentService.findPaymentsBetweenDates(fromDate, toDate, pageNo, pageSize, sortBy);
        return new ResponseEntity<>(paymentsBetweenDates, new HttpHeaders(), HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/search/{memberInvestId}/{fromDate}/{toDate}")
    ResponseEntity<PaymentView> findMemberPaymentsBetweenDates(
            @PathVariable String memberInvestId,
            @PathVariable(value = "fromDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
            @PathVariable(value = "toDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy) {

        PaymentView memberPaymentsBetweenDates = paymentService.findMemberPaymentsBetweenDates(
                memberInvestId, fromDate, toDate, pageNo, pageSize, sortBy);
        return new ResponseEntity<>(memberPaymentsBetweenDates, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/invest/{investmentId}")
    ResponseEntity<PaymentView> getPaymentsByInvestId(
            @PathVariable String investmentId,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy) {

        PaymentView paymentsByInvestId = paymentService.findPaymentByInvestId(investmentId, pageNo, pageSize, sortBy);

        return new ResponseEntity<>(paymentsByInvestId, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    PaymentDto getPaymentById(@PathVariable Long id) {
        Payment paymentById = paymentService.findPaymentById(id);
        return mapper.modelToDto(paymentById);
    }

    @PostMapping("/upload")
    void uploadStatement(@RequestParam("file") MultipartFile file) throws FileNotFoundException {
        paymentService.processCSVFile(file);
    }
}
