package elethu.ikamva.controllers;

import elethu.ikamva.exception.ResourceNotFoundException;
import elethu.ikamva.service.PaymentFileService;
import elethu.ikamva.view.PaymentFileResponseData;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payment")
@SecurityRequirement(name = "bearerAuth")
public class PaymentFileController {
    private final PaymentFileService paymentFileService;

    @GetMapping("/file/{id}")
    ResponseEntity<PaymentFileResponseData> getPaymentFile(HttpServletRequest request, @PathVariable Long id)
            throws ResourceNotFoundException {
        String buildFileUrl = paymentFileService.buildFileUrl(ServletUriComponentsBuilder.fromContextPath(request), id);
        PaymentFileResponseData data = paymentFileService.findFileById(id);
        data.setFileDownloadUri(buildFileUrl);
        return ResponseEntity.ok(data);
    }

    @GetMapping("/files")
    ResponseEntity<?> getAllPaymentFiles(HttpServletRequest request) {
        List<PaymentFileResponseData> paymentFiles = paymentFileService.findAllFiles();
        paymentFiles.forEach(file -> {
            String buildFileUrl =
                    paymentFileService.buildFileUrl(ServletUriComponentsBuilder.fromContextPath(request), file.getId());
            file.setFileDownloadUri(buildFileUrl);
        });
        return ResponseEntity.ok(paymentFiles);
    }
}
