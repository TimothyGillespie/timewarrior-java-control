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

import static org.junit.jupiter.api.Assertions.*;

class TrackingReadTest {

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

    @Test
    void testReadingExamplePartial() throws IOException {
        String json = FileLoader.load("gson/TimeWarriorJsonExamplePartialOneLine.json");
        Gson gson = new Gson().newBuilder().registerTypeAdapter(Tracking.class, new TrackingAdapter()).create();
        Tracking testTracking = gson.fromJson(json, Tracking.class);

        assertEquals(
                LocalDateTime.of(LocalDate.of(2021, 4, 17), LocalTime.of(17, 24, 31)),
                testTracking.getStartTime()
        );

        assertNull(testTracking.getEndTime());

        assertEquals(2, testTracking.getTags().size());
        assertTrue(testTracking.getTags().contains(new Tag("tag1")));
        assertTrue(testTracking.getTags().contains(new Tag("tag 2")));

        assertNull(testTracking.getAnnotation());
    }

    @Test
    void testReadingExampleFullHasCorrectOrderOfTags() throws IOException {
        String json = FileLoader.load("gson/TimeWarriorJsonExamplePartialOneLine.json");
        Gson gson = new Gson().newBuilder().registerTypeAdapter(Tracking.class, new TrackingAdapter()).create();
        Tracking testTracking = gson.fromJson(json, Tracking.class);

        assertEquals(
                new Tag("tag 1"),
                testTracking.getTags().get(0),
                "The tags don't seem to be parsed in the right order."
        );

        assertEquals(
                new Tag("tag2"),
                testTracking.getTags().get(1),
                "The tags don't seem to be parsed in the right order."
        );
    }
}
