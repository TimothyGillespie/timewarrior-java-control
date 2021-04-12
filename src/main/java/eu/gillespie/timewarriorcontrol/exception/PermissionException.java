package eu.gillespie.timewarriorcontrol.exception;

import eu.gillespie.timewarriorcontrol.Permission;

public class PermissionException extends RuntimeException {
    public PermissionException(String message) {
        super(message);
    }

}
