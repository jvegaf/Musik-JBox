package me.jvegaf.musikbox.shared.domain;

import java.util.Objects;

public abstract class StringValueObject {

    private final String value;

    public StringValueObject(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    @Override public int hashCode() {
        return Objects.hash(value);
    }

    @Override public boolean equals(Object o) {
        if (this==o) {
            return true;
        }
        if (!(o instanceof StringValueObject that)) {
            return false;
        }
        return Objects.equals(value, that.value);
    }

    @Override public String toString() {
        return this.value();
    }
}
