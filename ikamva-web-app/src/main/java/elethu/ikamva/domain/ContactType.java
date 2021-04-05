package elethu.ikamva.domain;

public enum ContactType {
    CELLPHONE("Cellphone"),
    EMAIL("Email"),
    HOME_PHONE("Home Number"),
    WORK_PHONE("Work Number");

    public final String value;

    ContactType(String s){this.value = s;}
}
