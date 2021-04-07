package eu.gillespie.timewarriorcontrol;

import eu.gillespie.timewarriorcontrol.exception.VersionFormatException;
import lombok.Getter;
import lombok.NonNull;

import java.util.Objects;

@Getter
public class Version implements Comparable<Version>{

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Version version = (Version) o;
        return getMajorVersion() == version.getMajorVersion() && getMinorVersion() == version.getMinorVersion() && getPatchVersion() == version.getPatchVersion();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMajorVersion(), getMinorVersion(), getPatchVersion());
    }

    @Override
    public int compareTo(@NonNull Version o) {
        if(getMajorVersion() != o.getMajorVersion())
            return getMajorVersion() - o.getMajorVersion();

        if(getMinorVersion() != o.getMinorVersion())
            return getMinorVersion() - o.getMinorVersion();

        return getPatchVersion() - o.getPatchVersion();
    }
}
