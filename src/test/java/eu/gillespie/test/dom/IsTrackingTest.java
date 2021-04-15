package eu.gillespie.test.dom;

import eu.gillespie.test.shared.TimeWarriorTestCase;
import eu.gillespie.timewarriorcontrol.TimeWarrior;
import eu.gillespie.timewarriorcontrol.exception.PermissionException;
import eu.gillespie.timewarriorcontrol.exception.VersionFormatException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class IsTrackingTest extends TimeWarriorTestCase {
    @Test
    void testIsTrackingIfNotTracking() throws VersionFormatException, IOException {
        assertFalse(tw.allowReading().isTracking());
    }

    /*
     * Error Cases
     */

    @Test
    void testCannotCallWithoutReadingPermission() {
        assertThrows(PermissionException.class, () -> tw.isTracking());
    }
}
