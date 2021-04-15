package eu.gillespie.test.dom;

import eu.gillespie.test.shared.TimeWarriorTestCase;
import eu.gillespie.timewarriorcontrol.exception.DOMObjectNotFoundException;
import eu.gillespie.timewarriorcontrol.exception.PermissionException;
import eu.gillespie.timewarriorcontrol.exception.VersionFormatException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class GeneralDomTest extends TimeWarriorTestCase {

    /*
     * Error cases
     */

    @Test
    void testGetDom() throws VersionFormatException, IOException, DOMObjectNotFoundException {
        // time warrior only exits with an error when the dom path begins with "dom."
        assertThrows(DOMObjectNotFoundException.class, () -> tw.allowReading().get("dom.nonexistant"));
    }

    @Test
    void testCannotCallWithoutReadingPermission() {
        assertThrows(PermissionException.class, () -> tw.get("dom.active"));
    }

}
