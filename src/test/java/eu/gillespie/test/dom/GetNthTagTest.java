package eu.gillespie.test.dom;

import eu.gillespie.timewarriorcontrol.TimeWarrior;
import eu.gillespie.timewarriorcontrol.exception.PermissionException;
import eu.gillespie.timewarriorcontrol.exception.VersionFormatException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GetNthTagTest {

    /*
     * Error Cases
     */

    @Test
    void testThrowsIllegalArgumentExceptionForNegativeNumbers() {
        assertThrows(IllegalArgumentException.class, () -> new TimeWarrior().allowReading().getNthTag(-1));
    }

    @Test
    void testThrowsIllegalArgumentExceptionForZero() {
        assertThrows(IllegalArgumentException.class, () -> new TimeWarrior().allowReading().getNthTag(0));
    }

    @Test
    void testCannotCallWithoutReadingPermission() {
        assertThrows(PermissionException.class, () -> new TimeWarrior().getNthTag(1));
    }

    @Test
    void testReturnsNullWhenRequestingHigherThanThereAreTags() throws VersionFormatException, IOException {
        assertNull(new TimeWarrior().allowReading().getNthTag(1));
    }
}
