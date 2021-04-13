package eu.gillespie.test.dom;

import eu.gillespie.timewarriorcontrol.TimeWarrior;
import eu.gillespie.timewarriorcontrol.exception.VersionFormatException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GetTotalTagCountTest {

    @Test
    void testTotalTagCountWithNothingAdded() throws VersionFormatException, IOException {
        assertEquals(0, new TimeWarrior().allowReading().getTotalTagCount());
    }
}
