package eu.gillespie.timewarriorcontrol.exception;

public class DOMObjectNotFound extends Exception {
    public DOMObjectNotFound(String domPath) {
        super(String.format("The dom object with path %s could not be found", domPath));
    }
}
