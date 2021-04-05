package elethu.ikamva.commons;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class DateFormatter {
    /**
     * @return
     */
    public LocalDate GetTodayDate(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = dtf.format(LocalDate.now());
        LocalDate todayDate = LocalDate.parse(date);

        return  todayDate;
    }
}
