package eu.gillespie.test.version;

import eu.gillespie.timewarriorcontrol.Version;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertTrue;

class CompareToTest {

    @Test
    void testMajorVersionTrumpsMinorVersion() {
        ArrayList<Version> versions = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            versions.add(new Version(10 - i, i, new Random().nextInt()));
        }

        Version[] sortedVersions = versions.toArray(new Version[0]);
        Arrays.sort(sortedVersions);

        int lastVersion = -1;
        for(Version singleVersion : sortedVersions) {
            assertTrue(singleVersion.getMajorVersion() > lastVersion);
            lastVersion = singleVersion.getMajorVersion();
        }
    }

    @Test
    void testMajorVersionTrumpsPatchVersion() {
        ArrayList<Version> versions = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            versions.add(new Version(10 - i, new Random().nextInt(), i));
        }

        Version[] sortedVersions = versions.toArray(new Version[0]);
        Arrays.sort(sortedVersions);

        int lastVersion = -1;
        for(Version singleVersion : sortedVersions) {
            assertTrue(singleVersion.getMajorVersion() > lastVersion);
            lastVersion = singleVersion.getMajorVersion();
        }
    }

    @Test
    void testMinorVersionTrumpsPatchVersion() {
        int majorVersion = new Random().nextInt();
        ArrayList<Version> versions = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            versions.add(new Version(majorVersion, 10 - i, i));
        }

        Version[] sortedVersions = versions.toArray(new Version[0]);
        Arrays.sort(sortedVersions);

        int lastVersion = -1;
        for(Version singleVersion : sortedVersions) {
            assertTrue(singleVersion.getMinorVersion() > lastVersion);
            lastVersion = singleVersion.getMinorVersion();
        }
    }

    @Test
    void testSortsByPatchVersionOtherwise() {
        int majorVersion = new Random().nextInt();
        int minorVersion = new Random().nextInt();

        ArrayList<Version> versions = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            versions.add(new Version(majorVersion, minorVersion, 10 - i));
        }

        Version[] sortedVersions = versions.toArray(new Version[0]);
        Arrays.sort(sortedVersions);

        int lastVersion = -1;
        for(Version singleVersion : sortedVersions) {
            assertTrue(singleVersion.getPatchVersion() > lastVersion);
            lastVersion = singleVersion.getPatchVersion();
        }
    }

}
