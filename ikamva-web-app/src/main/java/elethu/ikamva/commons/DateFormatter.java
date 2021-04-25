package elethu.ikamva.commons;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class DateFormatter {
    /**
     * @return
     */
    public LocalDate returnLocalDate(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = dtf.format(LocalDate.now());
        return LocalDate.parse(date);
    }

    public LocalDateTime returnLocalDateTime() {
        return LocalDateTime.now();
    }

    public LocalDate returnLocalDate(String date){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(date, dtf);
    }
}
