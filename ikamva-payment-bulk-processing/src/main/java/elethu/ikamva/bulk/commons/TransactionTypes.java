package elethu.ikamva.bulk.commons;

public enum TransactionTypes {
    CREDIT("Credit"),
    DEBIT("Debit");
    public final String value;

    TransactionTypes(String s){value = s;}
}
