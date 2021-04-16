package eu.gillespie.test.dom;

import eu.gillespie.test.shared.TimeWarriorTestCase;
import eu.gillespie.timewarriorcontrol.Tag;
import eu.gillespie.timewarriorcontrol.Tracking;
import eu.gillespie.timewarriorcontrol.exception.PermissionException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.*;

class GetActiveTrackingTest extends TimeWarriorTestCase {

    @Test
    void testSimple() throws IOException, InterruptedException {
        testMasterTw.start("tag1", "tag2");
        LocalDateTime startTime = LocalDateTime.now();

        Tracking tracking = tw.allowReading().getActiveTracking();

        assertNotNull(tracking);

        assertEquals(2, tracking.getTags().size());
        assertTrue(
                tracking.getTags().contains(new Tag("tag1")),
                String.format("Did not contain tag1 as tag. Instead contained %s.", tracking.getTags().toString())
        );
        assertTrue(
                tracking.getTags().contains(new Tag("tag 2")),
                String.format("Did not contain tag 2 as tag. Instead contained %s.", tracking.getTags().toString())
        );

        assertTrue( (startTime.toEpochSecond(ZoneOffset.UTC) - tracking.getStartTime().toEpochSecond(ZoneOffset.UTC)) <= 1L);

    }

    /**
     * Error Cases
     */

    @Test
    void testCannotCallWithoutReadingPermission() {
        assertThrows(PermissionException.class, () -> tw.getActiveTracking());
    }

}
