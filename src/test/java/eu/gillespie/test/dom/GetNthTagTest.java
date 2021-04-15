package eu.gillespie.test.dom;

import eu.gillespie.test.shared.TimeWarriorTestCase;
import eu.gillespie.timewarriorcontrol.exception.PermissionException;
import eu.gillespie.timewarriorcontrol.exception.VersionFormatException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GetNthTagTest extends TimeWarriorTestCase {

    /*
     * Error Cases
     */

    @Test
    void testThrowsIllegalArgumentExceptionForNegativeNumbers() {
        assertThrows(IllegalArgumentException.class, () -> tw.allowReading().getNthTag(-1));
    }

    @Test
    void testThrowsIllegalArgumentExceptionForZero() {
        assertThrows(IllegalArgumentException.class, () -> tw.allowReading().getNthTag(0));
    }

    @Test
    void testCannotCallWithoutReadingPermission() {
        assertThrows(PermissionException.class, () -> tw.getNthTag(1));
    }

    @Test
    void testReturnsNullWhenRequestingHigherThanThereAreTags() throws VersionFormatException, IOException {
        assertNull(tw.allowReading().getNthTag(1));
    }
}
