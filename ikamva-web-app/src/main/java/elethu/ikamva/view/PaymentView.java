package elethu.ikamva.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import elethu.ikamva.domain.Payment;
import lombok.Data;

import java.util.List;

@Data
public class PaymentView {
    private List<Payment> payments;
    private String page;
    private int size;
    @JsonProperty("total amount")
    private Double total;
}
