package eu.gillespie.timewarriorcontrol.exception;

import eu.gillespie.timewarriorcontrol.Permission;

public class PermissionException extends RuntimeException {
    public PermissionException(Permission permission) {
        super("The permission could not be assigned because it was disabled by the config key " +
                permission.getConfigKey()
        );
    }

}
