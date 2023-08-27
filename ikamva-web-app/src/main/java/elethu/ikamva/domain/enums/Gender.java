package elethu.ikamva.domain.enums;

import lombok.Getter;

@Getter
public enum Gender {
    MALE("Male"),
    FEMALE("Female");

    public final String value;

    Gender(String value) {
        this.value = value;
    }
}
