package eu.gillespie.test.control;

import eu.gillespie.test.shared.TimeWarriorTestCase;
import eu.gillespie.timewarriorcontrol.TimeWarrior;
import eu.gillespie.timewarriorcontrol.exception.PermissionException;
import eu.gillespie.timewarriorcontrol.exception.VersionFormatException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class StartTest extends TimeWarriorTestCase {

    @Test
    void testStartActivatesTracking() throws IOException, InterruptedException {
        assertFalse(readingTw.isTracking());
        tw.start();
        assertTrue(readingTw.isTracking());
    }

    /*
     * Error Cases
     */

    @Test
    void testFailsWithoutWritingPermission() {
        assertThrows(PermissionException.class, () -> tw.start());
    }
}
