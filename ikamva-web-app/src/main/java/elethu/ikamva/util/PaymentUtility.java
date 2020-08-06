package elethu.ikamva.util;

import elethu.ikamva.services.PaymentService;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class PaymentUtility {

    public Boolean checkPayment(double payment, String investmentID, Date paymentDate){
        Boolean paymentExist = false;

        PaymentService paymentService;


        return paymentExist;
    }
}
