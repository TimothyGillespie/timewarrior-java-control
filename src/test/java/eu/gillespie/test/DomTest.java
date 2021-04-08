package eu.gillespie.test;

import eu.gillespie.timewarriorcontrol.TimeWarrior;
import eu.gillespie.timewarriorcontrol.exception.DOMObjectNotFoundException;
import eu.gillespie.timewarriorcontrol.exception.VersionFormatException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class DomTest {

    @Test
    void testGetDom() throws VersionFormatException, IOException, DOMObjectNotFoundException {
        // time warrior only exits with an error when the dom path begins with "dom."
        assertThrows(DOMObjectNotFoundException.class, () -> new TimeWarrior().get("dom.nonexistant"));
    }
}
