package eu.gillespie.test.dom;

import eu.gillespie.test.shared.TimeWarriorTestCase;
import eu.gillespie.timewarriorcontrol.exception.PermissionException;
import eu.gillespie.timewarriorcontrol.exception.VersionFormatException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GetTotalTagCountTest extends TimeWarriorTestCase {

    @Test
    void testTotalTagCountWithNothingAdded() throws VersionFormatException, IOException {
        assertEquals(0, tw.allowReading().getTotalTagCount());
    }

    /*
     * Error Cases
     */
    
    @Test
    void testCannotCallWithoutReadingPermission() {
        assertThrows(PermissionException.class, () -> tw.getTotalTagCount());
    }
}
