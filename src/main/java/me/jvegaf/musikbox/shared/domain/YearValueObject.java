package me.jvegaf.musikbox.shared.domain;

import java.util.Objects;

public abstract class YearValueObject {

    private String value;

    public YearValueObject(String value) {
        this.value = validate(value);
    }

    public String value() { return value; }

    @Override public int hashCode() {
        return Objects.hash(value);
    }

    @Override public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof YearValueObject)) {
            return false;
        }
        YearValueObject that = (YearValueObject) o;
        return Objects.equals(value, that.value);
    }

    @Override public String toString() {
        return this.value();
    }

    private String validate(String value) {
        if (value.isEmpty()) {
            throw new IllegalArgumentException("Year cannot be empty");
        }
        if (!value.matches("[0-9]+")) {
            throw new IllegalArgumentException("Year must be a number");
        }

        return value.length() > 4 ? value.substring(0, 4) : value;
    }
}