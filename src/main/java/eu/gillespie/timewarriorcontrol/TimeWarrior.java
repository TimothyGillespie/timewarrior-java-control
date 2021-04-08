package eu.gillespie.timewarriorcontrol;

import eu.gillespie.timewarriorcontrol.exception.DOMObjectNotFound;
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

    public String get(String domPath) throws DOMObjectNotFound {
        try {
            Process p = rt.exec(new String[]{
                    getCmdCommand(),
                    "get",
                    domPath
            });

            p.waitFor();

            if(p.exitValue() != 0)
                throw new DOMObjectNotFound(domPath);

            return readWhole(p.getInputStream()).trim();
        } catch (IOException | InterruptedException converted) {
            throw new DOMObjectNotFound(domPath);
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
