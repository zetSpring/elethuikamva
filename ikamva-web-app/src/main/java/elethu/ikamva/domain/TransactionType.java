package elethu.ikamva.domain;

public enum TransactionType {
    MONTHLY_CONTRIBUTION("Monthly Contribution"),
    BANK_CHARGES("Bank Charges");

    public final String value;

    TransactionType(String s){this.value = s;}
}
