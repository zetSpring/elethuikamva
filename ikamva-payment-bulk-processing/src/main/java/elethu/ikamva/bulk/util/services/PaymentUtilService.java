package elethu.ikamva.bulk.util.services;

import java.util.Date;

public interface PaymentUtilService {
    String ExtractInvestID(String paymentRef);
    Boolean PaymentCheck(double payment, String investmentID, Date paymentDate);
}
