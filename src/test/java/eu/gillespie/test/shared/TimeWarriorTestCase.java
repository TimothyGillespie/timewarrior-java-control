package eu.gillespie.test.shared;

import eu.gillespie.timewarriorcontrol.Permission;
import eu.gillespie.timewarriorcontrol.TimeWarrior;
import eu.gillespie.timewarriorcontrol.exception.PermissionException;
import eu.gillespie.timewarriorcontrol.exception.VersionFormatException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.UUID;

/**
 * Purpose of this class is to be extend and to provide a TimeWarrior instance this way.
 */
public class TimeWarriorTestCase {
    protected TimeWarrior tw;

    @BeforeEach
    void setUpTw() throws VersionFormatException, IOException {
        this.tw = new TimeWarrior();
    }

    @AfterEach
    void resetTimeWarriorData() throws IOException, InterruptedException {

        if(!Permission.DELETE.canAllow())
            throw new PermissionException("Must be able to allow deleting to run tests.");

        String uuid = UUID.randomUUID().toString();

        Runtime r = Runtime.getRuntime();
        Process p;

        p = r.exec(new String[] {
                "/bin/sh",
                "-c",
                "mkdir -p ~/.timewarrior-control/" + uuid,
        });
        p.waitFor();

        p = r.exec(new String[] {
                "/bin/sh",
                "-c",
                "mv ~/.timewarrior ~/.timewarrior-control/" + uuid,
        });
        p.waitFor();

        p = r.exec(new String[] {
                "/bin/sh",
                "-c",
                "mkdir ~/.timewarrior",
        });
        p.waitFor();
    }
}
