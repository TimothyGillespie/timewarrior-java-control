package eu.gillespie.timewarriorcontrol.exception;

public class DOMObjectNotFoundException extends Exception {
    public DOMObjectNotFoundException(String domPath) {
        super(String.format("The dom object with path %s could not be found", domPath));
    }
}
