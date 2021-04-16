package eu.gillespie.timewarriorcontrol;

import lombok.Getter;
import lombok.NonNull;

import java.util.Objects;

@Getter
public class Tag {
    private final String name;

    public Tag(@NonNull String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tag tag = (Tag) o;
        return Objects.equals(getName(), tag.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }

    @Override
    public String toString() {
        return this.name;
    }
}
