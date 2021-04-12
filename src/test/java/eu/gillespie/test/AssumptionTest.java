package eu.gillespie.test;

import eu.gillespie.timewarriorcontrol.Permission;
import eu.gillespie.timewarriorcontrol.TimeWarrior;
import eu.gillespie.timewarriorcontrol.Version;
import eu.gillespie.timewarriorcontrol.exception.VersionFormatException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

/*
 * This class is supposed to test for assumptions we make during and for our tests.
 */
public class AssumptionTest {

    @Test
    void assumeCorrectTimeWarriorVersionRange() throws VersionFormatException, IOException {
        Version runningVersion = new TimeWarrior().getVersion();

        Version minimumVersion = new Version(1,4,2);
        Version maximumVersionExclusive = new Version(2,0,0);

        assertTrue(

                runningVersion.compareTo(minimumVersion) >= 0,

                String.format(
                    "Running version was %s but must be at least %s.",
                    runningVersion.toString(),
                    minimumVersion.toString()
                )
        );

        assertTrue(

                runningVersion.compareTo(maximumVersionExclusive) < 0,

                String.format(
                    "Running version was %s but must lower than %s.",
                    runningVersion.toString(),
                    minimumVersion.toString()
                )
        );
    }

    @Test
    void assumeCanAllowReading() throws IOException {
        assertTrue(Permission.READ.canAllow());
    }

    @Test
    void assumeCanAllowWriting() throws IOException {
        assertTrue(Permission.WRITE.canAllow());
    }

    @Test
    void assumeCanAllowDeleting() throws IOException {
        assertTrue(Permission.DELETE.canAllow());
    }
}
