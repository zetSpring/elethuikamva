package elethu.ikamva.view;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({
    "id",
    "size",
    "numberOfRecords",
    "fileTotalAmount",
    "fileType",
    "fileUploadedDate",
    "fileName",
    "fileDownloadUri",
    "fileData"
})
public class PaymentFileResponseData {
    private Long id;
    private long size;
    private String fileName;
    private String fileType;
    private byte[] fileData;
    private int numberOfRecords;
    private String fileDownloadUri;
    private Double fileTotalAmount;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MMM-yyyy:HH:mm:ss")
    private LocalDateTime fileUploadedDate;
}
