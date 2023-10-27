package elethu.ikamva.service.Impl;

import elethu.ikamva.domain.PaymentFile;
import elethu.ikamva.exception.ResourceNotFoundException;
import elethu.ikamva.repositories.PaymentFileRepository;
import elethu.ikamva.service.PaymentFileService;
import elethu.ikamva.view.PaymentFileResponseData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Lazy
@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentFileServiceImpl implements PaymentFileService {
    private final PaymentFileRepository paymentFileRepository;

    @Override
    public PaymentFileResponseData findFileById(Long id) throws ResourceNotFoundException {
        if (Objects.isNull(id)) {
            log.error("File id is null");
            throw new IllegalArgumentException("File id is null");
        }

        Optional<PaymentFile> paymentFileOptional = paymentFileRepository.findById(id);

        PaymentFile paymentFile = paymentFileOptional.stream()
                .filter(Objects::nonNull)
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("File with id " + id + " does not exist"));

        return this.buildPaymentFileResponseData(paymentFile);
    }

    @Override
    public List<PaymentFileResponseData> findAllFiles() {
        List<PaymentFileResponseData> paymentFileResponseData = paymentFileRepository.findAll().stream()
                .filter(Objects::nonNull)
                .map(this::buildPaymentFileResponseData)
                .toList();

        paymentFileResponseData.forEach(file -> file.setSize(file.getFileData().length));

        return paymentFileResponseData;
    }

    @Override
    public String buildFileUrl(ServletUriComponentsBuilder servletUriComponentsBuilder, Long id) {
        if (Objects.isNull(id)) {
            log.error("File id is null");
            throw new IllegalArgumentException("File id is null");
        }

        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/payment/file/")
                .path(id.toString())
                .toUriString();
    }

    @Override
    public PaymentFile saveFile(PaymentFile paymentFile) {
        if (Objects.isNull(paymentFile)) {
            log.error("Payment file is null");
            throw new IllegalArgumentException("Payment file is null");
        }

        Optional<PaymentFile> foundFileRecord = paymentFileRepository.findByFileNameAndFileTotalAmountAndNumberOfTransactions(
                paymentFile.getFileName(), paymentFile.getFileTotalAmount(), paymentFile.getNumberOfTransactions());

        if (foundFileRecord.isPresent()) {
            log.info("File with name: {} already exists.", paymentFile.getFileName());
            return foundFileRecord.get();
        }

        return paymentFileRepository.save(paymentFile);
    }

    private PaymentFileResponseData buildPaymentFileResponseData(PaymentFile paymentFile) {
        return PaymentFileResponseData.builder()
                .id(paymentFile.getId())
                .fileName(paymentFile.getFileName())
                .fileType(paymentFile.getFileType())
                .numberOfRecords(paymentFile.getNumberOfTransactions())
                .fileTotalAmount(paymentFile.getFileTotalAmount())
                .fileData(paymentFile.getFileData())
                .fileUploadedDate(paymentFile.getFileUploadedDate())
                .build();
    }
}
