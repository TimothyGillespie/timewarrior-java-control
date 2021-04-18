package eu.gillespie.timewarriorcontrol.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Parser {

    private Parser() {}

    public static LocalDateTime parseUserviewDateDate(String dateString) {
        dateString = dateString.trim();
        return LocalDateTime.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
    }
}
