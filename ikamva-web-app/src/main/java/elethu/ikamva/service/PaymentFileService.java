package elethu.ikamva.service;

import elethu.ikamva.domain.PaymentFile;
import elethu.ikamva.exception.ResourceNotFoundException;
import elethu.ikamva.view.PaymentFileResponseData;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

public interface PaymentFileService {
    PaymentFileResponseData findFileById(Long id) throws ResourceNotFoundException;

    List<PaymentFileResponseData> findAllFiles();

    PaymentFile saveFile(PaymentFile paymentFile);

    String buildFileUrl(ServletUriComponentsBuilder servletUriComponentsBuilder, Long id);
}
