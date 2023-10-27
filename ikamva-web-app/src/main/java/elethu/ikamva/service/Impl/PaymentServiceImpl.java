package elethu.ikamva.service.Impl;

import elethu.ikamva.aspects.ExecutionTime;
import elethu.ikamva.commons.DateFormatter;
import elethu.ikamva.domain.Member;
import elethu.ikamva.domain.Payment;
import elethu.ikamva.domain.PaymentFile;
import elethu.ikamva.domain.enums.TransactionType;
import elethu.ikamva.exception.MemberException;
import elethu.ikamva.exception.PaymentException;
import elethu.ikamva.repositories.PaymentRepository;
import elethu.ikamva.service.MemberService;
import elethu.ikamva.service.PaymentFileService;
import elethu.ikamva.service.PaymentService;
import elethu.ikamva.utils.CSVPaymentProcessor;
import elethu.ikamva.view.PaymentView;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final MemberService memberService;
    private final PaymentRepository paymentRepository;
    private final PaymentFileService paymentFileService;

    @Override
    @ExecutionTime
    public Payment savePayment(Payment payment) {
        if (isPaymentActive(payment.getAmount(), payment.getInvestmentId(), payment.getPaymentDate())) {
            throw new PaymentException(HttpStatus.INTERNAL_SERVER_ERROR.value()
                    + ", there is already a a similar payment on the same day fot the same member: "
                    + payment.getInvestmentId());
        }

        Member member = Optional.of(memberService.findMemberByInvestmentId(payment.getInvestmentId()))
                .orElseThrow(() -> new PaymentException(HttpStatus.INTERNAL_SERVER_ERROR.value()
                        + ", could not add new payment for:: " + payment.getInvestmentId()));

        payment.setMemberPayments(member);
        payment.setTransactionType(getTransactionType(payment.getAmount()));
        payment.setCreatedDate(DateFormatter.returnLocalDateTime());

        return paymentRepository.save(payment);
    }

    @Override
    @ExecutionTime
    public Map<Double, Map<Integer, List<Payment>>> bulkSavePayments(List<Payment> payments) {
        AtomicInteger successCounter = new AtomicInteger(0);
        AtomicInteger failedCounter = new AtomicInteger(0);
        AtomicReference<Double> totalAmount = new AtomicReference<>(0d);
        AtomicReference<List<Payment>> paymentAtomicReference = new AtomicReference<>(new ArrayList<>());
        payments.forEach(payment -> {
            var paymentExists =
                    isPaymentActive(payment.getAmount(), payment.getInvestmentId(), payment.getPaymentDate());
            try {
                Member member = memberService.findMemberByInvestmentId(payment.getInvestmentId());
                if (paymentExists) {
                    log.error(
                            "Payment for investment id: {} with amount: {} on date: {} already exists.",
                            payment.getInvestmentId(),
                            payment.getAmount(),
                            payment.getPaymentDate());
                } else {
                    payment.setMemberPayments(member);
                    payment.setCreatedDate(DateFormatter.returnLocalDateTime());
                    payment.setTransactionType(getTransactionType(payment.getAmount()));
                    Payment savedPayment = paymentRepository.save(payment);
                    successCounter.getAndIncrement();
                    totalAmount.set(payment.getAmount());
                    paymentAtomicReference.get().add(savedPayment);
                }
            } catch (MemberException e) {
                failedCounter.getAndIncrement();
                log.error("Did not find member with investment id: {}", payment.getInvestmentId());
            }
        });
        log.info("Total number of successful payments made: {}", successCounter);
        log.info("Total number of unsuccessful payments is: {}", failedCounter);

        return Map.of(totalAmount.get(), Map.of(successCounter.get(), paymentAtomicReference.get()));
    }

    @Override
    @ExecutionTime
    public Payment updatePayment(Payment payment) {
        paymentRepository
                .findPaymentByIdAndEndDateIsNull(payment.getId())
                .orElseThrow(() -> new PaymentException(
                        String.format("Payment with id: %d does not exist, cannot update payment", payment.getId())));

        return paymentRepository.save(payment);
    }

    @Override
    @ExecutionTime
    public Payment deletePayment(Long id) {
        var pay = paymentRepository
                .findById(id)
                .orElseThrow(() -> new PaymentException(
                        HttpStatus.INTERNAL_SERVER_ERROR.value() + ", Could not find payment for with id: " + id));
        pay.setEndDate(DateFormatter.returnLocalDateTime());

        return paymentRepository.save(pay);
    }

    @Override
    @ExecutionTime
    public Payment findPaymentById(Long id) {
        return paymentRepository
                .findPaymentByIdAndEndDateIsNull(id)
                .orElseThrow(() -> new PaymentException(
                        HttpStatus.INTERNAL_SERVER_ERROR.value() + ", Could not find payment for payment id: " + id));
    }

    @Override
    @ExecutionTime
    public PaymentView findPaymentByInvestId(String investmentId, int pageNo, int pageSize, String sortBy) {
        var paging = PageRequest.of(pageNo - 1, pageSize, Sort.by(sortBy));
        var memberPaymentList = paymentRepository.findPaymentsByInvestmentIdAndEndDateIsNull(investmentId, paging);

        return this.paymentView(memberPaymentList);
    }

    @Override
    @ExecutionTime
    public PaymentView findPaymentsBetweenDates(
            LocalDate fromDate, LocalDate toDate, int pageNo, int pageSize, String sortBy) {
        var pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        var memberPayments = paymentRepository.findPaymentsBetween(
                DateFormatter.returnLocalDate(fromDate.toString()),
                DateFormatter.returnLocalDate(toDate.toString()),
                pageable);

        return this.paymentView(memberPayments);
    }

    @Override
    @ExecutionTime
    public PaymentView findMemberPaymentsBetweenDates(
            String memberInvestId, LocalDate fromDate, LocalDate toDate, int pageNo, int pageSize, String sortBy) {
        var pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        var memberPayments =
                paymentRepository.findPaymentsByDateRangeForMember(memberInvestId, fromDate, toDate, pageable);

        return this.paymentView(memberPayments);
    }

    @Override
    @ExecutionTime
    public boolean isPaymentActive(double paymentAmount, String investmentID, LocalDate paymentDate) {
        return paymentRepository
                .checkPayment(paymentAmount, investmentID, paymentDate)
                .isPresent();
    }

    @Override
    @ExecutionTime
    public void processCSVFile(MultipartFile csvFile) {
        log.info("ServiceInvocation::ProcessCSVFile");
        try {
            if (!CSVPaymentProcessor.isCSVFormat(csvFile) || csvFile.isEmpty()) {
                log.error("Error with a file type");
                throw new FileNotFoundException("The file uploaded is not a CSV file, please correct and upload again");
            }

            List<Payment> csvBulkPayments = CSVPaymentProcessor.bulkCSVFileProcessing(csvFile.getInputStream());
            log.info("Total csv records: {}", csvBulkPayments.size());

            if (csvBulkPayments.isEmpty()) {
                log.error("No payments found in the file.");
                throw new PaymentException("No payments found in the csv file");
            }

            csvBulkPayments.forEach(payment -> {
                try {
                    Member member = memberService.findMemberByInvestmentId(payment.getInvestmentId());
                    payment.setMemberPayments(member);
                    payment.setTransactionType(getTransactionType(payment.getAmount()));
                    payment.setCreatedDate(DateFormatter.returnLocalDateTime());
                } catch (MemberException e) {
                    log.error("Could not find member with investment id: {}", payment.getInvestmentId());
                }
            });

            List<Payment> filteredPayments = csvBulkPayments.stream()
                    .filter(p -> Objects.nonNull(p.getCreatedDate()))
                    .toList();

            Double totalFileAmount =
                    filteredPayments.stream().map(Payment::getAmount).reduce(0.0, Double::sum);

            PaymentFile paymentFile = PaymentFile.builder()
                    .fileTotalAmount(totalFileAmount)
                    .fileType(csvFile.getContentType())
                    .fileData(csvFile.getBytes())
                    .fileName(csvFile.getOriginalFilename())
                    .numberOfTransactions(filteredPayments.size())
                    .fileUploadedDate(DateFormatter.returnLocalDateTime())
                    .build();

            paymentFile.setPayments(filteredPayments);
            PaymentFile savedPaymentFile = paymentFileService.saveFile(paymentFile);
            log.info("Total number of payments saved: {}", savedPaymentFile.getId());
        } catch (IOException e) {
            log.info("There was a problem with the usre of the CSV processor", e);
            throw new PaymentException(
                    "There was a problem processing uploaded file, please make sure its a csv file.");
        }
    }

    private TransactionType getTransactionType(double amount) {
        return amount > 0 ? TransactionType.MONTHLY_CONTRIBUTION : TransactionType.BANK_CHARGES;
    }

    private PaymentView paymentView(Page<Payment> payments) {
        PaymentView paymentView = new PaymentView();

        paymentView.setPayments(payments.getContent());
        paymentView.setPage(String.format("%s of %s", payments.getNumber() + 1, payments.getTotalPages()));
        paymentView.setSize(payments.getContent().size());
        var pageTotal = payments.getContent().stream()
                .filter(payment -> payment.getTransactionType().equals(TransactionType.MONTHLY_CONTRIBUTION))
                .map(Payment::getAmount)
                .reduce(0.0, Double::sum);

        paymentView.setTotal(pageTotal);

        return paymentView;
    }

    private List<Payment> returnPayments(Page<Payment> payments) {
        return payments.hasContent() ? payments.getContent() : Collections.emptyList();
    }
}
