package eu.gillespie.timewarriorcontrol.exception;

public class VersionFormatException extends Exception {
    public VersionFormatException() {
        super("The version did not have the format of x.x.x where x is an integer and could thus not be read.");
    }
}
