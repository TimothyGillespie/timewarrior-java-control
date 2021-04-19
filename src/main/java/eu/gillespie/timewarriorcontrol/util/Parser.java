package eu.gillespie.timewarriorcontrol.util;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Parser {

    private Parser() {}

    public static final DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    public static LocalDateTime parseUserviewDateDateTime(String dateString, LocalDateTime fillDateTime) {
        dateString = dateString.trim();

        long dashCount = dateString.chars().filter(ch -> ch == '-').count();
        long semicolonCount = dateString.chars().filter(ch -> ch == ':').count();

        if(dashCount == 2)
            return LocalDateTime.parse(dateString, pattern);
        if(dashCount == 1)
            return LocalDateTime.parse(String.format("%d-%s", fillDateTime.getYear(), dateString), pattern);
        if(dashCount == 0)
            if(semicolonCount == 2)
                try {
                    return LocalDateTime.parse(String.format("%d-%02d-%s", fillDateTime.getYear(), fillDateTime.getMonthValue(), dateString), pattern);
                } catch (DateTimeParseException e) {
                        return LocalDateTime.of(
                                fillDateTime.toLocalDate(),
                                LocalTime.parse(dateString, DateTimeFormatter.ofPattern("HH:mm:ss"))
                        );
                }
            if(semicolonCount == 1)
                return LocalDateTime.parse(String.format(
                            "%d-%02d-%02dT%02d:%s",
                            fillDateTime.getYear(),
                            fillDateTime.getMonthValue(),
                            fillDateTime.getDayOfMonth(),
                            fillDateTime.getHour(),
                            dateString
                        ),
                        pattern
                        );
            if(semicolonCount == 0)
                try {
                    return LocalDateTime.parse(String.format(
                            "%d-%02d-%02dT%02d:%02d:%s",
                            fillDateTime.getYear(),
                            fillDateTime.getMonthValue(),
                            fillDateTime.getDayOfMonth(),
                            fillDateTime.getHour(),
                            fillDateTime.getMinute(),
                            dateString
                            ),
                            pattern
                    );
                } catch (DateTimeParseException e) {
                    return fillDateTime;
                }


        throw new IllegalArgumentException();
    }
}
