package eu.gillespie.timewarriorcontrol;

import eu.gillespie.timewarriorcontrol.exception.DOMObjectNotFoundException;
import eu.gillespie.timewarriorcontrol.exception.PermissionException;
import eu.gillespie.timewarriorcontrol.exception.VersionFormatException;
import eu.gillespie.timewarriorcontrol.terminal.SyncTerminalHandler;
import eu.gillespie.timewarriorcontrol.terminal.SyncTerminalInfo;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

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


    public TimeWarrior() throws IOException, VersionFormatException, InterruptedException {
        this("timew");
    }

    public TimeWarrior(String cmdCommand) throws IOException, VersionFormatException, InterruptedException {
        this.cmdCommand = cmdCommand;

        String versionString = SyncTerminalHandler.exec(new String[] {getCmdCommand(), "--version"})
                .getTerminalOutput().trim();

        this.version = new Version(versionString);
    }

    /**
     * Starts time tracking with the given tags. If there is an already active time tracking it will stop the current
     * tracking and then create the new one.
     *
     * @param tags The tags, as strings, which should be associated with this tracking.
     * @return The Tracking object of the just started tracking.
     *
     */
    public Tracking start(String... tags) throws IOException, InterruptedException {
        this.checkPermission(Permission.WRITE);

        List<String> cmdList = new LinkedList<>();
        cmdList.add(this.cmdCommand);
        cmdList.add("start");
        cmdList.addAll(Arrays.asList(tags));

        SyncTerminalHandler.exec(cmdList.toArray(new String[0]));

        return null;
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
            SyncTerminalInfo terminalInfo = SyncTerminalHandler.exec(new String[]{
                    getCmdCommand(),
                    "get",
                    domPath
            });

            if(terminalInfo.getExitCode() != 0)
                throw new DOMObjectNotFoundException(domPath);

            return terminalInfo.getTerminalOutput().trim();
        } catch (IOException | InterruptedException converted) {
            throw new DOMObjectNotFoundException(domPath);
        }
    }

    /**
     * Gets the total number of tags used across all trackings.
     * It uses the DOM path dom.tag.count
     *
     * @return The total number of all tags; 0 if an error occurs (i.e. could not find timew).
     * @throws PermissionException if the read permission is not allowed per twjc.properties
     */
    public int getTotalTagCount() {
        try {
            return Integer.parseInt(this.get("dom.tag.count"));
        } catch (DOMObjectNotFoundException | NumberFormatException ignored) {
            return 0;
        }
    }

    /**
     * Gets the n-th of tags used across all trackings.
     * It uses the DOM path dom.tag.n where n is the number given
     *
     * @param n The number of the tag to be rertrieved. Must be posititve, non-zero and less or equal to the number of tags
     * @return The requested tag; null if an error occurs (i.e. could not find timew).
     * @throws PermissionException if the read permission is not allowed per twjc.properties
     * @throws IllegalArgumentException if n is negative or zero.
     */
    public Tag getNthTag(int n) {
        if(n < 1)
            throw new IllegalArgumentException("n must be posititve and non-zero but was " + String.valueOf(n));

        try {
            return new Tag(this.get("dom.tag." + String.valueOf(n)));
        } catch (DOMObjectNotFoundException ignored) {
            return null;
        }
    }



    /**
     * Retrieves the information if any tracking is currently active.
     * This is the case if there is a tracking without and end time.
     * It uses the DOM path dom.active.
     *
     * @return true if there is a on going tracking, false otherwise or if an error occurs (i.e. could not find timew).
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
     * Retrieves the information of the currently active tracking.
     * This usually contains all information except the end time.
     * This uses the DOM path dom.active.json.
     *
     * @return The currently active tracking, null if no tracking is ongoing.
     */
    public Tracking getActiveTracking() {
        try {
            return Tracking.fromJson(this.get("dom.active.json"));
        } catch (DOMObjectNotFoundException ignored) {
            return null;
        }
    }

    /**
     * Enables this instance to perform reading actions like summary.
     *
     * @return The TimeWarrior instance it was called on for chaining.
     * @throws PermissionException if the read permission is not allowed per twjc.properties
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
