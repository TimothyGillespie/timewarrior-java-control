package eu.gillespie.test.parsing;

import eu.gillespie.test.shared.FileLoader;
import eu.gillespie.timewarriorcontrol.util.Parser;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserviewDateTimeParserTest {

    LocalDateTime targetValue = LocalDateTime.of(
            LocalDate.of(2021, 4, 19),
            LocalTime.of(19, 24, 48)
    );

    @Test
    void testUntilMonth() throws IOException {
        String userviewDateTime = FileLoader.load("userview/datetime/untilMonth.txt");
        LocalDateTime parsedDateTime = Parser.parseUserviewDateDateTime(
                userviewDateTime,
                targetValue
                    .withSecond(0)
                    .withMinute(0)
                    .withHour(0)
                    .withDayOfMonth(1)
                    .withMonth(1)
        );

        assertEquals(targetValue, parsedDateTime);
    }

    @Test
    void testUntilDay() throws IOException {
        String userviewDateTime = FileLoader.load("userview/datetime/untilDay.txt");
        LocalDateTime parsedDateTime = Parser.parseUserviewDateDateTime(
                userviewDateTime,
                targetValue
                        .withSecond(0)
                        .withMinute(0)
                        .withHour(0)
                        .withDayOfMonth(1)
        );

        assertEquals(targetValue, parsedDateTime);
    }

    @Test
    void testUntilHour() throws IOException {
        String userviewDateTime = FileLoader.load("userview/datetime/untilHour.txt");
        LocalDateTime parsedDateTime = Parser.parseUserviewDateDateTime(
                userviewDateTime,
                targetValue
                        .withSecond(0)
                        .withMinute(0)
                        .withHour(0)
        );

        assertEquals(targetValue, parsedDateTime);
    }

    @Test
    void testUntilMinute() throws IOException {
        String userviewDateTime = FileLoader.load("userview/datetime/untilMinute.txt");
        LocalDateTime parsedDateTime = Parser.parseUserviewDateDateTime(
                userviewDateTime,
                targetValue
                        .withSecond(0)
                        .withMinute(0)
        );

        assertEquals(targetValue, parsedDateTime);
    }

    @Test
    void testUntilSecond() throws IOException {
        String userviewDateTime = FileLoader.load("userview/datetime/untilSecond.txt");
        LocalDateTime parsedDateTime = Parser.parseUserviewDateDateTime(
                userviewDateTime,
                targetValue.withSecond(0)
        );

        assertEquals(targetValue, parsedDateTime);
    }
}
