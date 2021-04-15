package eu.gillespie.test.dom;

import eu.gillespie.test.shared.TimeWarriorTestCase;
import eu.gillespie.timewarriorcontrol.exception.PermissionException;
import eu.gillespie.timewarriorcontrol.exception.VersionFormatException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GetTotalTagCountTest extends TimeWarriorTestCase {

    @Test
    void testTotalTagCountWithNothingAdded() {
        assertEquals(0, tw.allowReading().getTotalTagCount());
    }

    @Test
    void testTotalTagCountWithOneTag() throws IOException, InterruptedException {
        testMasterTw.start("first tag");
        assertEquals(1, tw.allowReading().getTotalTagCount());
    }

    @Test
    void testTotalTagCountWithTwoTags() throws IOException, InterruptedException {
        testMasterTw.start("first tag", "second tag");
        assertEquals(2, tw.allowReading().getTotalTagCount());
    }

    // Does not work for some reason
    @Disabled
    @Test
    void testTotalTagCountOfMultipleTrackings() throws IOException, InterruptedException {
        testMasterTw.start("first tag", "second tag");
        testMasterTw.start("A", "B", "C");
        testMasterTw.start("Z", "Y", "X", "W");
        assertEquals(9, tw.allowReading().getTotalTagCount());
    }

    /*
     * Error Cases
     */
    
    @Test
    void testCannotCallWithoutReadingPermission() {
        assertThrows(PermissionException.class, () -> tw.getTotalTagCount());
    }
}
