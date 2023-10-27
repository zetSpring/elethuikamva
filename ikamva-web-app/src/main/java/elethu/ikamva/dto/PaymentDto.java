package elethu.ikamva.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import elethu.ikamva.domain.PaymentFile;
import elethu.ikamva.domain.enums.TransactionType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class PaymentDto {
    @Id
    Long id;

    Double amount;
    String investId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MMM-yyyy")
    LocalDate paymentDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MMM-yyyy:HH:mm:ss")
    LocalDateTime createdDate;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MMM-yyyy:HH:mm:ss")
    LocalDateTime endDate;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    String deleteReason;
    String paymentReference;
    TransactionType transactionType;
    PaymentFile paymentFile;
}
