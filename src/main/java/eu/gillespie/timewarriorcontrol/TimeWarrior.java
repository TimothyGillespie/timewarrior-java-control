package eu.gillespie.timewarriorcontrol;

import eu.gillespie.timewarriorcontrol.exception.DOMObjectNotFoundException;
import eu.gillespie.timewarriorcontrol.exception.PermissionException;
import eu.gillespie.timewarriorcontrol.exception.VersionFormatException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Getter
@Setter
public class TimeWarrior {

    private final String cmdCommand;
    private final Version version;

    private boolean hasReadPermission = false;
    private boolean hasWritePermission = false;
    private boolean hasDeletePermission = false;

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
     * @throws PermissionException if the instance has no reading permission.
     * @throws DOMObjectNotFoundException if timewarrior exits with an error code or another error occurs
     */
    public String get(String domPath) throws DOMObjectNotFoundException {
        this.checkPermission(Permission.READ);

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
     * @throws PermissionException if the instance has no reading permission.
     */
    public boolean isTracking() {
        try {
            return this.get("dom.active").trim().equals("1");
        } catch (DOMObjectNotFoundException ignored) {
            return false;
        }
    }

    /**
     * Enables this instance to perform reading actions like summary.
     *
     * @return The TimeWarrior instance it was called on for chaining.
     * @throws PermissionException if the permission is not allowed per twjc.properties
     */
    public TimeWarrior allowReading() {
        if(!Permission.READ.canAllow())
            throw new PermissionException(String.format(
                    "The permission could not be assigned because it was disabled by the config key %s.canAllow",
                    Permission.READ.getConfigKey()
            ));

        this.setHasReadPermission(true);
        return this;
    }

    /**
     * Disables this instance to perform reading actions like summary.
     *
     * @return The TimeWarrior instance it was called on for chaining.
     */
    public TimeWarrior forbidReading() {
        this.setHasReadPermission(false);
        return this;
    }

    /**
     * Enables this instance to perform writing actions like start.
     *
     * @return The TimeWarrior instance it was called on for chaining.
     * @throws PermissionException if the permission is not allowed per twjc.properties
     */
    public TimeWarrior allowWriting() {
        if(!Permission.WRITE.canAllow())
            throw new PermissionException(String.format(
                    "The permission could not be assigned because it was disabled by the config key %s.canAllow",
                    Permission.WRITE.getConfigKey()
            ));

        this.setHasWritePermission(true);
        return this;
    }

    /**
     * Disables this instance to perform writing actions like start.
     *
     * @return The TimeWarrior instance it was called on for chaining.
     */
    public TimeWarrior forbidWriting() {
        this.setHasWritePermission(false);
        return this;
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

    /**
     * Checks if the needed permission has been given.
     *
     * @param neededPermission The permission which is to be checked.
     * @throws PermissionException if the permission has not been given.
     */
    private void checkPermission(Permission neededPermission) {
        String missingPermission = null;

        if(neededPermission.equals(Permission.READ) && !this.hasReadPermission)
            missingPermission = "read";

        if(neededPermission.equals(Permission.WRITE) && !this.hasWritePermission)
            missingPermission = "write";

        if(neededPermission.equals(Permission.DELETE) && !this.hasDeletePermission)
            missingPermission = "delete";

        if(missingPermission != null)
            throw new PermissionException(String.format("Instance has no %s permission", missingPermission));
    }
}
