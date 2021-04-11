package eu.gillespie.timewarriorcontrol;

import eu.gillespie.timewarriorcontrol.exception.DOMObjectNotFoundException;
import eu.gillespie.timewarriorcontrol.exception.VersionFormatException;
import lombok.AccessLevel;
import lombok.Getter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Getter
public class TimeWarrior {

    private final String cmdCommand;

    // The following refers to
    private final Version version;

    @Getter(AccessLevel.NONE)
    private Runtime rt = Runtime.getRuntime();


    public TimeWarrior() throws IOException, VersionFormatException {
        this("timew");
    }

    public TimeWarrior(String cmdCommand) throws IOException, VersionFormatException {
        this.cmdCommand = cmdCommand;

        Process p = rt.exec(new String[] {getCmdCommand(), "--version"});
        String versionString = readWhole(p.getInputStream());

        this.version = new Version(versionString);
    }

    /**
     * Retrieves the raw information as a String for a given DOM path.
     * It uses the timew get command.
     *
     * @param domPath The DOM path to request. Starts with "dom."
     * @return The returned information
     * @throws DOMObjectNotFoundException if timewarrior exits with an error code or another error occurs
     */
    public String get(String domPath) throws DOMObjectNotFoundException {
        try {
            Process p = rt.exec(new String[]{
                    getCmdCommand(),
                    "get",
                    domPath
            });

            p.waitFor();

            if(p.exitValue() != 0)
                throw new DOMObjectNotFoundException(domPath);

            return readWhole(p.getInputStream()).trim();
        } catch (IOException | InterruptedException converted) {
            throw new DOMObjectNotFoundException(domPath);
        }
    }

    /**
     * Retrieves the information if any tracking is currently active.
     * This is the case if there is a tracking without and end time.
     * It uses the DOM path dom.active.
     *
     * @return true if there is a on going tracking, false otherwise or if an error occurs (i.e. could not find timew)
     */
    public boolean isTracking() {
        try {
            return this.get("dom.active").trim().equals("1");
        } catch (DOMObjectNotFoundException ignored) {
            return false;
        }
    }


    private String readWhole(InputStream in) {
        BufferedReader bf = new BufferedReader(new InputStreamReader(in));
        StringBuilder builder = new StringBuilder();

        String line;

        try {
            while ((line = bf.readLine()) != null)
                builder.append(line);
        } catch (IOException ignored) {}

        return builder.toString();
    }
}
