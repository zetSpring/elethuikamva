package elethu.ikamva.bulk.util.services;

import elethu.ikamva.bulk.commons.TransactionTypes;

import java.util.Date;

public interface PaymentUtilService {
    String ExtractInvestID(String paymentRef);
    boolean PaymentCheck(double payment, String investmentID, Date paymentDate);
    TransactionTypes GetTransactionType(double amount);
}
