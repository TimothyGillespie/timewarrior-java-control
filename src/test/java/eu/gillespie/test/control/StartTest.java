package eu.gillespie.test.control;

import eu.gillespie.test.shared.TimeWarriorTestCase;
import eu.gillespie.timewarriorcontrol.Tag;
import eu.gillespie.timewarriorcontrol.commandinfo.StartInfo;
import eu.gillespie.timewarriorcontrol.exception.PermissionException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StartTest extends TimeWarriorTestCase {

    static final List<Tag> tagList1 = Arrays.asList(
            new Tag("tag1"),
            new Tag("tag 2")
    );

    static final List<Tag> tagList2 = Arrays.asList(
            new Tag("tag 2"),
            new Tag("tag3")
    );

    @Test
    void testStartActivatesTracking() throws IOException, InterruptedException {
        assertFalse(testMasterTw.isTracking());
        tw.allowWriting().start();
        assertTrue(testMasterTw.isTracking());
    }

    @Test
    void testStartWithoutPreviousTaskIsAsExpected() throws IOException, InterruptedException {
        StartInfo startInfo = tw.allowWriting().start("tag1", "tag 2");

        assertNull(startInfo.getPreviousTracking());

        assertNotNull(startInfo.getCurrentTracking());
        assertEquals(tagList1, startInfo.getCurrentTracking().getTags());


        assertEquals(tagList1, startInfo.getNewTags());
    }

    @Test
    void testStartWithPreviousTaskIsAsExpected() throws IOException, InterruptedException {
        testMasterTw.start("tag1", "tag 2");

        StartInfo startInfo = tw.allowWriting().start("tag 2", "tag3");

        assertNotNull(startInfo.getPreviousTracking());
        assertEquals(tagList1, startInfo.getPreviousTracking().getTags());

        assertNotNull(startInfo.getCurrentTracking());
        assertEquals(tagList2, startInfo.getCurrentTracking().getTags());

        assertEquals(Collections.singletonList(new Tag("tag3")), startInfo.getNewTags());

    }

    /*
     * Error Cases
     */

    @Test
    void testFailsWithoutWritingPermission() {
        assertThrows(PermissionException.class, () -> tw.start());
    }
}
