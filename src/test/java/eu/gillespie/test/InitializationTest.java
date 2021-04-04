package eu.gillespie.test;

import eu.gillespie.timewarriorcontrol.TimeWarrior;
import eu.gillespie.timewarriorcontrol.exception.VersionFormatException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class InitializationTest {

    @Test
    void testVersion() throws IOException, VersionFormatException {
        TimeWarrior tw = new TimeWarrior();
        System.out.println(tw.getVersion().getMajorVersion());
    }
}
