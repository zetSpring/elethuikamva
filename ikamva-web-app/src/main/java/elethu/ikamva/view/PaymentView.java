package elethu.ikamva.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import elethu.ikamva.domain.Payment;
import lombok.Data;

import java.util.List;

@Data
@JsonPropertyOrder({"page", "size", "pageTotalAmount", "payments"})
public class PaymentView {
    private int size;
    private String page;

    @JsonProperty("pageTotalAmount")
    private Double total;

    private List<Payment> payments;
}
