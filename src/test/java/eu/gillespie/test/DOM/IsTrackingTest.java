package eu.gillespie.test.DOM;

import eu.gillespie.timewarriorcontrol.TimeWarrior;
import eu.gillespie.timewarriorcontrol.exception.VersionFormatException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class IsTrackingTest {
    @Test
    void testIsTrackingIfNotTracking() throws VersionFormatException, IOException {
        assertFalse(new TimeWarrior().isTracking());
    }
}
