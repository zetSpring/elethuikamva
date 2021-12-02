package elethu.ikamva.commons;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class DateFormatter {
    /**
     * @return
     */
    public LocalDate returnLocalDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = dtf.format(LocalDate.now());
        return LocalDate.parse(date);
    }

    public LocalDateTime returnLocalDateTime() {
        return LocalDateTime.now();
    }

    public LocalDate returnLocalDate(String date) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(date, dtf);
    }

    public static LocalDate csvDateExtract(String date) {
        String csvDay = date.substring(0, 2);
        String csvMonth = date.substring(2, 5);
        String csvYear = date.substring(5, 9);
        String csvDate = String.format("%s-%s-%s",csvDay, csvMonth, csvYear);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
        return LocalDate.parse(csvDate, dtf);
    }
}
