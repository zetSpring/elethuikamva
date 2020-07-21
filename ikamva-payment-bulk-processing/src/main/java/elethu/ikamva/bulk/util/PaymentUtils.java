package elethu.ikamva.bulk.util;

import elethu.ikamva.bulk.util.services.PaymentUtilService;

import java.util.Date;

public class PaymentUtils implements PaymentUtilService {

    @Override
    public String ExtractInvestID(String paymentRef) {
        String investID = "";
        String investmentID = "";

        for(int index = paymentRef.length()-1; index >= 0; index--){
            int i = index;
            if ((paymentRef.charAt(index) == '5') && (paymentRef.charAt(index-1) == '1')){ //)
                investID = "";
                for (int a = index; a >= 0; a--){
                    investID = investID + paymentRef.charAt(a);

                    if (paymentRef.charAt(a) == ' '){
                        break;
                    }
                }
            }
        }

        for(int p = investID.length()-1; p >= 0; p--){
            investmentID = investmentID + investID.charAt(p);
        }

        return investmentID;
    }

    @Override
    public Boolean PaymentCheck(double payment, String investmentID, Date paymentDate) {
        return null;
    }
}
