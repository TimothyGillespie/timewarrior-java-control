package eu.gillespie.timewarriorcontrol;

import lombok.Getter;
import lombok.NonNull;

@Getter
public class Tag {
    private final String name;

    public Tag(@NonNull String name) {
        this.name = name;
    }
}
