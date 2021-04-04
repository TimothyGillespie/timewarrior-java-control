package eu.gillespie.timewarriorcontrol;

import eu.gillespie.timewarriorcontrol.exception.VersionFormatException;
import lombok.Getter;

@Getter
public class Version {

    private final int majorVersion;
    private final int minorVersion;
    private final int patchVersion;

    public Version(String versionString) throws VersionFormatException {
        String[] versionNumbers = versionString.trim().split("\\.");
        if(versionNumbers.length != 3)
            throw new VersionFormatException();

        try {
            this.majorVersion = Integer.parseInt(versionNumbers[0]);
            this.minorVersion = Integer.parseInt(versionNumbers[1]);
            this.patchVersion = Integer.parseInt(versionNumbers[2]);
        } catch (NumberFormatException converted) {
            throw new VersionFormatException();
        }
    }

    public Version(int majorVersion, int minorVersion, int patchVersion) {
        this.majorVersion = majorVersion;
        this.minorVersion = minorVersion;
        this.patchVersion = patchVersion;
    }

    @Override
    public String toString() {
        return String.format("%d.%d.%d", getMajorVersion(), getMinorVersion(), getPatchVersion());
    }
}
