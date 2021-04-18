package eu.gillespie.test.parsing;

import eu.gillespie.test.shared.FileLoader;
import eu.gillespie.timewarriorcontrol.Tag;
import eu.gillespie.timewarriorcontrol.Tracking;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class TrackingTimewStartUserViewTest {

    @Test
    void test18MonthRecordedExample() throws IOException {
        String recorded18Month = FileLoader.load("userview/tracking-raw/timewTrackingRecordedUserview18months.txt");
        Tracking parsedTracking = Tracking.fromUserView(recorded18Month);

        assertEquals(
            LocalDateTime.of(
                LocalDate.of(2019, 10, 26),
                LocalTime.of(18,28,1)
            ),
            parsedTracking.getStartTime()
        );

        assertEquals(
                LocalDateTime.of(
                        LocalDate.of(2021, 4, 18),
                        LocalTime.of(18,28,20)
                ),
                parsedTracking.getEndTime()
        );

        assertEquals(
                new ArrayList<>(Collections.singletonList(new Tag("tag"))),
                parsedTracking.getTags()
         );

        assertNull(parsedTracking.getAnnotation());
    }

    @Test
    void testRealLifeActiveExample() throws IOException {
        String recorded18Month = FileLoader.load("userview/tracking-raw/timewTrackingRecordedUserview18months.txt");
        Tracking parsedTracking = Tracking.fromUserView(recorded18Month);

        assertEquals(
                LocalDateTime.of(
                        LocalDate.of(2021, 4, 18),
                        LocalTime.of(19,47,10)
                ),
                parsedTracking.getStartTime()
        );

        assertNull(parsedTracking.getEndTime());

        assertEquals(
                new ArrayList<>(Arrays.asList(
                        new Tag("development"),
                        new Tag("opensource"))
                ),
                parsedTracking.getTags()
        );

        assertNull(parsedTracking.getAnnotation());
    }
}
