package eu.gillespie.test.parsing;

import eu.gillespie.test.shared.FileLoader;
import eu.gillespie.timewarriorcontrol.Tag;
import eu.gillespie.timewarriorcontrol.commandinfo.StartInfo;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class StartInfoUserViewTest {

    @Test
    void testSimpleExample() throws IOException {
        String example = FileLoader.load("userview/timew-start/timewStartSimple.txt");
        StartInfo startInfo = StartInfo.fromUserView(example);

        assertEquals(1, startInfo.getNewTags().size());
        assertTrue(startInfo.getNewTags().contains(new Tag("newtag3")));

        assertNull(startInfo.getPreviousTracking());

        assertEquals(
                LocalDateTime.of(
                        LocalDate.of(2021,4,18),
                        LocalTime.of(10,7,6)
                ),
                startInfo.getCurrentTracking().getStartTime(),
                "Current Tracking start time did not match."
        );

        assertNull(startInfo.getCurrentTracking().getEndTime());

        assertEquals(
                Collections.singletonList(new Tag("newtag3")),
                startInfo.getCurrentTracking().getTags()
        );

        assertNull(startInfo.getCurrentTracking().getAnnotation());
    }

    @Test
    void testComplexExample() throws IOException {
        String example = FileLoader.load("userview/timew-start/timewStartComplex.txt");
        StartInfo startInfo = StartInfo.fromUserView(example);

        LinkedList<Tag> expectedNewTags = new LinkedList<Tag>(Arrays.asList(
                new Tag("@_[]^/{}*#$|~´¯¿⅞°*Ω_™±°!>ß<>=&?()-:%;,."),
                new Tag("more stuff"),
                new Tag("morestuff"),
                new Tag("test"),
                new Tag("tiest!=<]_"),
                new Tag("understanding something")
        ));

        assertEquals(expectedNewTags, startInfo.getNewTags());

        assertNull(startInfo.getPreviousTracking());

        assertEquals(
                LocalDateTime.of(
                        LocalDate.of(2021,4,18),
                        LocalTime.of(10,25,0)
                ),
                startInfo.getCurrentTracking().getStartTime(),
                "Current Tracking start time did not match."
        );

        assertNull(startInfo.getCurrentTracking().getEndTime());

        assertEquals(
                // Is same with all tags
                expectedNewTags,
                startInfo.getCurrentTracking().getTags()
        );

        assertNull(startInfo.getCurrentTracking().getAnnotation());
    }

    @Test
    void testComplexExampleWithTaskSwitch() throws IOException {
        String example = FileLoader.load("userview/timew-start/timewStartComplexWithTaskSwitch.txt");
        StartInfo startInfo = StartInfo.fromUserView(example);

        LinkedList<Tag> expectedNewTags = new LinkedList<Tag>(Arrays.asList(
                new Tag("computer"),
                new Tag("new @_[]^/{}*#$|~´¯¿⅞°*Ω_™±°!>ß<>=&?()-:%;,.")
        ));

        LinkedList<Tag> expectedTagsPrevious = new LinkedList<Tag>(Arrays.asList(
                new Tag("@_[]^/{}*#$|~´¯¿⅞°*Ω_™±°!>ß<>=&?()-:%;,."),
                new Tag("more stuff"),
                new Tag("morestuff"),
                new Tag("test"),
                new Tag("tiest!=<]_"),
                new Tag("understanding something")
        ));

        LinkedList<Tag> expectedTagsCurrent = new LinkedList<Tag>(Arrays.asList(
                new Tag("computer"),
                new Tag("more stuff"),
                new Tag("morestuff"),
                new Tag("new @_[]^/{}*#$|~´¯¿⅞°*Ω_™±°!>ß<>=&?()-:%;,."),
                new Tag("test"),
                new Tag("tiest!=<]_"),
                new Tag("understanding something")
        ));

        // General
        assertEquals(expectedNewTags, startInfo.getNewTags());

        // Previous Tracking
        assertNull(startInfo.getPreviousTracking().getAnnotation());
        assertEquals(
                expectedTagsPrevious,
                startInfo.getCurrentTracking().getTags()
        );

        assertEquals(
                LocalDateTime.of(
                        LocalDate.of(2021,4,18),
                        LocalTime.of(10,25,0)
                ),
                startInfo.getPreviousTracking().getStartTime(),
                "Previous Tracking start time did not match."
        );

        assertEquals(
                LocalDateTime.of(
                        LocalDate.of(2021,4,18),
                        LocalTime.of(10,27,8)
                ),
                startInfo.getPreviousTracking().getEndTime(),
                "Previous Tracking end time did not match."
        );


        // Current Tracking

        assertNull(startInfo.getCurrentTracking().getAnnotation());
        assertEquals(
                expectedTagsCurrent,
                startInfo.getCurrentTracking().getTags()
        );

        assertEquals(
                LocalDateTime.of(
                        LocalDate.of(2021,4,18),
                        LocalTime.of(10,25,0)
                ),
                startInfo.getCurrentTracking().getStartTime(),
                "Current Tracking start time did not match."
        );

        assertNull(startInfo.getCurrentTracking().getEndTime());
    }

    @Test
    void testRealLifeExampleWithTaskSwitch() throws IOException {
        String example = FileLoader.load("userview/timew-start/realLifeTimewStartWithTaskSwitch.txt");
        StartInfo startInfo = StartInfo.fromUserView(example);

        LinkedList<Tag> expectedNewTags = new LinkedList<Tag>(Arrays.asList(
                new Tag("new"),
                new Tag("tags")
        ));

        LinkedList<Tag> expectedTagsPrevious = new LinkedList<Tag>(Arrays.asList(
                new Tag("development"),
                new Tag("opensource")
        ));

        LinkedList<Tag> expectedTagsCurrent = new LinkedList<Tag>(Arrays.asList(
                new Tag("development"),
                new Tag("new"),
                new Tag("tags")
        ));

        // General
        assertEquals(expectedNewTags, startInfo.getNewTags());

        // Previous Tracking
        assertNull(startInfo.getPreviousTracking().getAnnotation());
        assertEquals(
                expectedTagsPrevious,
                startInfo.getCurrentTracking().getTags()
        );

        assertEquals(
                LocalDateTime.of(
                        LocalDate.of(2021,4,18),
                        LocalTime.of(10,35,14)
                ),
                startInfo.getPreviousTracking().getStartTime(),
                "Previous Tracking start time did not match."
        );

        assertEquals(
                LocalDateTime.of(
                        LocalDate.of(2021,4,18),
                        LocalTime.of(12,33,57)
                ),
                startInfo.getPreviousTracking().getEndTime(),
                "Previous Tracking end time did not match."
        );


        // Current Tracking

        assertNull(startInfo.getCurrentTracking().getAnnotation());
        assertEquals(
                expectedTagsCurrent,
                startInfo.getCurrentTracking().getTags()
        );

        assertEquals(
                LocalDateTime.of(
                        LocalDate.of(2021,4,18),
                        LocalTime.of(10,33,57)
                ),
                startInfo.getCurrentTracking().getStartTime(),
                "Current Tracking start time did not match."
        );

        assertNull(startInfo.getCurrentTracking().getEndTime());
    }
}
