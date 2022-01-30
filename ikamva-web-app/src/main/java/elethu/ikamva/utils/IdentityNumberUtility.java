package elethu.ikamva.utils;

import elethu.ikamva.exception.MemberException;

import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Objects;

public class IdentityNumberUtility {
    public static String getMemberGender(String genderNumbers) {
        if (Objects.isNull(genderNumbers) || genderNumbers.length() != 4) {
            throw new MemberException("The gender id numbers provided are invalid");
        }

        return Long.parseLong(genderNumbers) >= 5000 ? "M" : "F";
    }

    public static LocalDate getDateOfBirth(String identityDob){
        if (Objects.isNull(identityDob) || identityDob.length() != 6) {
            throw new MemberException("Invalid identity date of birth");
        }

        String day = identityDob.substring(4, 6);
        String month = identityDob.substring(2, 4);
        String year = identityDob.substring(0, 2);

        String birthDate = String.format("%s%s%s", day, month, year);
        DateTimeFormatter dtf = new DateTimeFormatterBuilder().appendPattern("ddMM")
                .appendValueReduced(ChronoField.YEAR, 2, 2, Year.now().getValue() - 110).toFormatter();

        return LocalDate.parse(birthDate, dtf);
    }
}
