package eu.gillespie.test;

import eu.gillespie.timewarriorcontrol.TimeWarrior;
import eu.gillespie.timewarriorcontrol.exception.DOMObjectNotFound;
import eu.gillespie.timewarriorcontrol.exception.VersionFormatException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class DomTest {

    @Test
    void testGetDom() throws VersionFormatException, IOException, DOMObjectNotFound {
        // time warrior only exits with an error when the dom path begins with "dom."
        assertThrows(DOMObjectNotFound.class, () -> new TimeWarrior().get("dom.nonexistant"));
    }
}
