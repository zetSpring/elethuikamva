package elethu.ikamva.service.Impl;

import elethu.ikamva.commons.DateFormatter;
import elethu.ikamva.domain.Member;
import elethu.ikamva.domain.Payment;
import elethu.ikamva.domain.enums.Gender;
import elethu.ikamva.domain.enums.TransactionType;
import elethu.ikamva.exception.MemberException;
import elethu.ikamva.exception.PaymentException;
import elethu.ikamva.repositories.PaymentRepository;
import elethu.ikamva.service.MemberService;
import elethu.ikamva.view.PaymentView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceImplTest {
    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private MemberService memberService;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    private Payment payment;
    private Member member;
    private Payment newPayment;

    @BeforeEach
    void setUp() {
        member = new Member(1L, Long.parseLong("0804268523085"), "KK012015", "Emihle", "Yawa", DateFormatter.returnLocalDate(), Gender.FEMALE);
        payment = new Payment(1L, 1000.0d, "KK012015", DateFormatter.returnLocalDate().minusDays(1), DateFormatter.returnLocalDateTime(), "KK012015", TransactionType.MONTHLY_CONTRIBUTION, member);
        newPayment = new Payment(2L, 1500.0d, "KK012015", DateFormatter.returnLocalDate().minusDays(10), DateFormatter.returnLocalDateTime(), "KK012015", TransactionType.MONTHLY_CONTRIBUTION, member);
    }

    @Test
    void savePayment() {
        //given
        given(memberService.findMemberByInvestmentId(anyString())).willReturn(member);
        given(paymentRepository.save(any())).willReturn(payment);

        //when
        Payment savePayment = paymentService.savePayment(payment);

        //then
        then(memberService).should(atLeastOnce()).findMemberByInvestmentId(anyString());
        then(paymentRepository).should(atLeastOnce()).save(any());
        assertThat(savePayment).isNotNull();
        assertThat(savePayment).isEqualTo(payment);
    }

    @Test
    void bulkSavePayments() {
        //given
        List<Payment> bulkPayments = new ArrayList<>();
        bulkPayments.add(payment);
        bulkPayments.add(newPayment);
        given(memberService.findMemberByInvestmentId(anyString())).willReturn(member);

        //when
        paymentService.bulkSavePayments(bulkPayments);

        //then
        then(memberService).should(atLeast(2)).findMemberByInvestmentId(anyString());
    }

    @Test
    void bulkSavePaymentsFoundPayment() {
        //given
        List<Payment> bulkPayments = new ArrayList<>();
        Payment newPayment = new Payment(2L, 1500.0d, "KK012015", DateFormatter.returnLocalDate(), DateFormatter.returnLocalDateTime(), "KK012015", TransactionType.MONTHLY_CONTRIBUTION, member);
        bulkPayments.add(payment);
        bulkPayments.add(newPayment);
        given(memberService.findMemberByInvestmentId(anyString())).willReturn(member);
        given(paymentRepository.checkPayment(anyDouble(), anyString(), any())).willReturn(Optional.of(payment));

        //when
        paymentService.bulkSavePayments(bulkPayments);

        //then
        then(memberService).should(atLeast(2)).findMemberByInvestmentId(anyString());
    }

    @Test
    void updatePayment() {
        //given
        Payment updatePayment = new Payment(1L, 1500.0d, "KK012015", DateFormatter.returnLocalDate().minusDays(1), DateFormatter.returnLocalDateTime(), "KK012015", TransactionType.MONTHLY_CONTRIBUTION, member);
        when(paymentRepository.findPaymentById(anyLong())).thenReturn(Optional.ofNullable(payment));
        when(paymentRepository.save(any(Payment.class))).thenReturn(updatePayment);

        //when
        Payment updatedPayment = paymentService.updatePayment(updatePayment);

        //then
        then(paymentRepository).should(atLeastOnce()).findPaymentById(anyLong());
        then(paymentRepository).should(atLeastOnce()).save(any());
        assertThat(updatedPayment).isNotNull();
        assertThat(updatedPayment).isEqualTo(payment);
    }

    @Test
    void deletePayment() {
        //given
        when(paymentRepository.findById(anyLong())).thenReturn(Optional.ofNullable(payment));
        when(paymentRepository.save(any(Payment.class))).thenReturn(payment);

        //when
        Payment deletePayment = paymentService.deletePayment(1L);

        //then
        verify(paymentRepository, atLeastOnce()).findById(anyLong());
        verify(paymentRepository, atLeastOnce()).save(any(Payment.class));
        assertThat(deletePayment).isNotNull();
        assertThat(deletePayment.getEndDate()).isNotNull();
        assertThat(deletePayment.getEndDate()).isEqualTo(DateFormatter.returnLocalDate().toString());
    }

    @Test
    void findPaymentById() {
        //given
        given(paymentRepository.findPaymentById(anyLong())).willReturn(Optional.of(newPayment));

        //when
        Payment payment1 = paymentService.findPaymentById(2L);

        //then
        verify(paymentRepository).findPaymentById(2L);
        assertThat(payment1).isNotNull();
    }

    @Test
    void findPaymentByInvestId() {
        //given
        List<Payment> payments = new ArrayList<>();
        payments.add(payment);
        payments.add(newPayment);
        given(paymentRepository.findPaymentByInvestmentId(anyString(), any())).willReturn((new PageImpl<>(payments)));

        //when
        PaymentView paymentList = paymentService.findPaymentByInvestId("KK012015", 1, 1, "id");

        //then
        verify(paymentRepository).findPaymentByInvestmentId(anyString(), any());
        assertThat(paymentList.getPayments()).hasSize(2);
    }

    @Test
    void findPaymentsBetweenDates() {
        //given
        List<Payment> payments = new ArrayList<>();
        payments.add(payment);
        when(paymentRepository.findPaymentsBetween(any(LocalDate.class), any(LocalDate.class), any())).thenReturn(new PageImpl<>(payments));

        //when
        PaymentView paymentsBetweenDates = paymentService.findPaymentsBetweenDates(DateFormatter.returnLocalDate(), DateFormatter.returnLocalDate().minusDays(5), 1, 1, "id");

        //then
        then(paymentRepository).should().findPaymentsBetween(any(LocalDate.class), any(LocalDate.class), any());
        assertThat(paymentsBetweenDates).isNotNull();
        assertThat(paymentsBetweenDates.getPayments()).hasSize(1);
    }

    @Test
    void findMemberPaymentsBetweenDates() {
        //given
        List<Payment> payments = new ArrayList<>();
        payments.add(payment);
        payments.add(newPayment);
        when(paymentRepository.findPaymentsByDateRangeForMember(anyString(), any(LocalDate.class), any(LocalDate.class), any())).thenReturn(new PageImpl<>(payments));

        //when
        PaymentView memberPaymentsBetweenDates = paymentService.findMemberPaymentsBetweenDates("KK012015", DateFormatter.returnLocalDate(), DateFormatter.returnLocalDate().minusDays(11), 1, 2, "paymentDate");

        //then
        then(paymentRepository).should().findPaymentsByDateRangeForMember(anyString(), any(), any(), any());
        assertThat(memberPaymentsBetweenDates).isNotNull();
        assertThat(memberPaymentsBetweenDates.getPayments()).hasSize(2);
    }


    @Test
    void processCSVFile() throws IOException {
        //given
        String csvBuilder = "02Jan2020,SG012015,1100,144337.01" +
                System.getProperty("line.separator") +
                "07Jan2020,SM012015,1100,145437.01" +
                System.getProperty("line.separator") +
                "11Jan2020,CARRIED FORWARD,0,146537.01" +
                System.getProperty("line.separator") +
                "25Jan2020,SM012015,1100,147637.01" +
                System.getProperty("line.separator") +
                "28Jan2020,LG012015,1100,150937.01";
        InputStream is = new ByteArrayInputStream(csvBuilder.getBytes());
        MockMultipartFile multipartFile = new MockMultipartFile("file", "file.csv", "text/csv", is);
        given(memberService.findMemberByInvestmentId(anyString())).willReturn(member);

        //when
        paymentService.processCSVFile(multipartFile);

        //then
        then(memberService).should(atLeast(3)).findMemberByInvestmentId(anyString());
    }

    @Test
    void savePaymentIsPaymentActiveExceptionTest() {
        //given
        given(paymentRepository.checkPayment(anyDouble(), anyString(), any(LocalDate.class))).willReturn(Optional.ofNullable(payment));

        //when - then
        assertThrows(PaymentException.class, () -> paymentService.savePayment(payment));
    }

    @Test
    void savePaymentMembeIsNullTest() {
        //given
        given(memberService.findMemberByInvestmentId(anyString())).willReturn(null);

        //when - then
        assertThrows(PaymentException.class, () -> paymentService.savePayment(payment));
    }

    @Test
    void updatePaymentNotFoundExceptionTest() {
        //when - then
        assertThrows(PaymentException.class, () -> paymentService.updatePayment(payment));
    }

        @Test
    void findPaymentBetweenDatesExceptionTest() {
        //given
        when(paymentRepository.findPaymentsBetween(any(LocalDate.class), any(LocalDate.class), any())).thenReturn(Page.empty());

        //when
        PaymentView paymentsBetweenDates = paymentService.findPaymentsBetweenDates(DateFormatter.returnLocalDate(), DateFormatter.returnLocalDate().minusDays(5), 1, 1, "id");

        //then
        verify(paymentRepository).findPaymentsBetween(any(LocalDate.class), any(LocalDate.class), any());
        assertThat(paymentsBetweenDates.getPayments()).isEmpty();
    }

    @Test
    void findMemberPaymentsBetweenDatesEmptyList(){
        //given
        when(paymentRepository.findPaymentsByDateRangeForMember(anyString(), any(LocalDate.class), any(LocalDate.class), any())).thenReturn(Page.empty());

        //when
        PaymentView memberPaymentsBetweenDates = paymentService.findMemberPaymentsBetweenDates("KK012015", DateFormatter.returnLocalDate(), DateFormatter.returnLocalDate().minusDays(5), 1, 1, "id");

        //then
        verify(paymentRepository, atLeastOnce()).findPaymentsByDateRangeForMember(anyString(), any(LocalDate.class), any(LocalDate.class), any());
        assertThat(memberPaymentsBetweenDates.getPayments()).isEmpty();
    }

    @Test
    void processCSVFileNotCsvFileException() throws IOException {
        //given
        String csvBuilder = "02Jan2020,SG012015,1100,144337.01" +
                System.getProperty("line.separator") +
                "07Jan2020,SM012015,1100,145437.01";
        InputStream is = new ByteArrayInputStream(csvBuilder.getBytes());
        MockMultipartFile multipartFile = new MockMultipartFile("file", "file.csv", MediaType.APPLICATION_JSON_VALUE, is);

        //when
        assertThrows(PaymentException.class, () -> paymentService.processCSVFile(multipartFile));
    }

    @Test
    @DisplayName("Finding No Private Companies Expection (Empty List) - Test")
    void bulkSaveCatchBlockThrowsException(){
        //given
        List<Payment> bulkPayments = new ArrayList<>();
        Payment newPayment = new Payment(2L, 1500.0d, "KK012015", DateFormatter.returnLocalDate(), DateFormatter.returnLocalDateTime(), "KK012015", TransactionType.MONTHLY_CONTRIBUTION, member);
        bulkPayments.add(payment);
        bulkPayments.add(newPayment);
        when(memberService.findMemberByInvestmentId(anyString())).thenThrow(new MemberException("Booooom"));

        //when - then
        paymentService.bulkSavePayments(bulkPayments);
    }
}
