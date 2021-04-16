package eu.gillespie.test.gson;

import com.google.gson.Gson;
import eu.gillespie.test.shared.FileLoader;
import eu.gillespie.timewarriorcontrol.Tag;
import eu.gillespie.timewarriorcontrol.Tracking;
import eu.gillespie.timewarriorcontrol.gson.TrackingAdapter;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TrackingReadTest {

    @Test
    void testReadingExampleFull() throws IOException {
        String json = FileLoader.load("gson/TimeWarriorJsonExampleFull.json");
        Gson gson = new Gson().newBuilder().registerTypeAdapter(Tracking.class, new TrackingAdapter()).create();
        Tracking testTracking = gson.fromJson(json, Tracking.class);

        assertEquals(
                LocalDateTime.of(LocalDate.of(2021, 4, 9), LocalTime.of(20, 55, 01)),
                testTracking.getStartTime()
        );

        assertEquals(
                LocalDateTime.of(LocalDate.of(2021, 4, 9), LocalTime.of(22, 33, 24)),
                testTracking.getEndTime()
        );

        assertEquals(2, testTracking.getTags().size());
        assertTrue(testTracking.getTags().contains(new Tag("tag 1")));
        assertTrue(testTracking.getTags().contains(new Tag("tag2")));

        assertEquals("Simple Annotation", testTracking.getAnnotation());
    }



}
