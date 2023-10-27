package elethu.ikamva.service.Impl;

import elethu.ikamva.domain.Payment;
import elethu.ikamva.domain.PaymentFile;
import elethu.ikamva.exception.ResourceNotFoundException;
import elethu.ikamva.repositories.PaymentFileRepository;
import elethu.ikamva.repositories.PaymentRepository;
import elethu.ikamva.view.PaymentFileResponseData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.fasterxml.jackson.databind.type.LogicalType.DateTime;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class PaymentFileServiceImplTest {
    @Mock
    private PaymentFileRepository paymentFileRepository;

    @InjectMocks
    private PaymentFileServiceImpl paymentFileService;

    @BeforeEach
    void setUp() {

    }

    @Test
    @DisplayName("Find file by id throws IllegalArgumentException")
    void findFileByIdThrowsIllegalArgumentExceptionTest() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> paymentFileService.findFileById(null));

        assertEquals("File id is null", exception.getMessage());
    }

    @Test
    @DisplayName("Find file by id throws ResourceNotFoundException")
    void findFileByIdThrowsResourceNotFoundExceptionTest() {
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> paymentFileService.findFileById(1L));

        assertEquals("File with id 1 does not exist", exception.getMessage());
    }

    @Test
    @DisplayName("Find a file by id")
    void findFileByIdTest() throws ResourceNotFoundException {
        // given
        Payment file = new Payment();
        file.setId(1L);
        file.setAmount(100.00);
        file.setInvestmentId("AM012015");
        file.setPaymentDate(LocalDate.now());
        file.setCreatedDate(LocalDateTime.now());
        file.setPaymentDate(LocalDate.now());

        PaymentFile paymentFile = PaymentFile.builder()
                .id(1L)
                .fileName("test.txt")
                .fileType("text/plain")
                .fileData("test".getBytes())
                .fileUploadedDate(LocalDateTime.now())
                .fileTotalAmount(100.00)
                .numberOfTransactions(1)
                .payments(List.of(file))
                .build();

        given(paymentFileRepository.findById(1L)).willReturn(java.util.Optional.of(paymentFile));

        // when
        PaymentFileResponseData paymentFileResponseData = paymentFileService.findFileById(1L);

        // then
        then(paymentFileRepository).should().findById(1L);
        then(paymentFileRepository).shouldHaveNoMoreInteractions();
        assertEquals(1L, paymentFileResponseData.getId());
    }

    @Test
    @DisplayName("Find all files")
    void findAllFilesTest() {
        // given
        Payment file = new Payment();
        file.setId(1L);
        file.setAmount(100.00);
        file.setInvestmentId("AM012015");
        file.setPaymentDate(LocalDate.now());
        file.setCreatedDate(LocalDateTime.now());
        file.setPaymentDate(LocalDate.now());

        PaymentFile paymentFile = PaymentFile.builder()
                .id(1L)
                .fileName("test.txt")
                .fileType("text/plain")
                .fileData("test".getBytes())
                .fileUploadedDate(LocalDateTime.now())
                .fileTotalAmount(100.00)
                .numberOfTransactions(1)
                .payments(List.of(file))
                .build();

        given(paymentFileRepository.findAll()).willReturn(List.of(paymentFile));

        // when
        List<PaymentFileResponseData> paymentFileResponseData = paymentFileService.findAllFiles();

        // then
        then(paymentFileRepository).should().findAll();
        then(paymentFileRepository).shouldHaveNoMoreInteractions();
        assertEquals(1, paymentFileResponseData.size());
    }

    @Test
    @DisplayName("Build file url throws IllegalArgumentException")
    void buildFileUrlThrowsIllegalArgumentExceptionTest() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> paymentFileService.buildFileUrl(null, null));

        assertEquals("File id is null", exception.getMessage());
    }

    @Test
    @DisplayName("Build file url")
    void buildFileUrlTest() {
        // given
        //TODO: Fix this test
//        MockHttpServletRequest request = new MockHttpServletRequest();
//        request.setServerPort(8080);
//        request.setServerName("localhost");
//        request.setContextPath("/payment");
//
//
//        ServletUriComponentsBuilder b =  ServletUriComponentsBuilder.fromContextPath(request);
////        b.cu("http://localhost:8080");
////        String contextPath = "http://localhost:8080";
//        Long id = 1L;
//
//        // when
//        String fileUrl = paymentFileService.buildFileUrl(b, id);
//
//        // then
//        assertEquals("http://localhost:8080/payment/file/1", fileUrl);
    }

    @Test
    @DisplayName("Save file throws IllegalArgumentException")
    void saveFileThrowsIllegalArgumentExceptionTest() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> paymentFileService.saveFile(null));

        assertEquals("Payment file is null", exception.getMessage());
    }

    @Test
    @DisplayName("Save file")
    void saveFileTest() {
        // given
        Payment file = new Payment();
        file.setId(1L);
        file.setAmount(100.00);
        file.setInvestmentId("AM012015");
        file.setPaymentDate(LocalDate.now());
        file.setCreatedDate(LocalDateTime.now());
        file.setPaymentDate(LocalDate.now());

        PaymentFile paymentFile = PaymentFile.builder()
                .id(1L)
                .fileName("test.txt")
                .fileType("text/plain")
                .fileData("test".getBytes())
                .fileUploadedDate(LocalDateTime.now())
                .fileTotalAmount(100.00)
                .numberOfTransactions(1)
                .payments(List.of(file))
                .build();

        given(paymentFileRepository.findByFileNameAndFileTotalAmountAndNumberOfTransactions(anyString(), anyDouble(), anyInt())).willReturn(Optional.of(paymentFile));

        // when
        PaymentFile savedFile = paymentFileService.saveFile(paymentFile);

        // then
        then(paymentFileRepository).should().findByFileNameAndFileTotalAmountAndNumberOfTransactions(anyString(), anyDouble(), anyInt());
        then(paymentFileRepository).shouldHaveNoMoreInteractions();
        assertEquals(1L, savedFile.getId());
    }

    @Test
    @DisplayName("Save file when file does not exist")
    void saveFileWhenFileDoesNotExistTest() {
        // given
        Payment file = new Payment();
        file.setId(1L);
        file.setAmount(100.00);
        file.setInvestmentId("AM012015");
        file.setPaymentDate(LocalDate.now());
        file.setCreatedDate(LocalDateTime.now());
        file.setPaymentDate(LocalDate.now());

        PaymentFile paymentFile = PaymentFile.builder()
                .id(1L)
                .fileName("test.txt")
                .fileType("text/plain")
                .fileData("test".getBytes())
                .fileUploadedDate(LocalDateTime.now())
                .fileTotalAmount(100.00)
                .numberOfTransactions(1)
                .payments(List.of(file))
                .build();

        given(paymentFileRepository.findByFileNameAndFileTotalAmountAndNumberOfTransactions(anyString(), anyDouble(), anyInt())).willReturn(Optional.empty());
        given(paymentFileRepository.save(any(PaymentFile.class))).willReturn(paymentFile);

        // when
        PaymentFile savedFile = paymentFileService.saveFile(paymentFile);

        // then
        then(paymentFileRepository).should().findByFileNameAndFileTotalAmountAndNumberOfTransactions(anyString(), anyDouble(), anyInt());
        then(paymentFileRepository).should().save(any(PaymentFile.class));
        then(paymentFileRepository).shouldHaveNoMoreInteractions();
        assertEquals(1L, savedFile.getId());
    }


}