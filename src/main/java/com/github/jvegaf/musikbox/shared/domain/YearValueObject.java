package com.github.jvegaf.musikbox.shared.domain;

import java.util.Objects;

public abstract class YearValueObject {

    private final String value;

    public YearValueObject(String value) {
        this.value = validate(value);
    }

    private String validate(String value) {
        return value.length() > 4 ? value.substring(0, 4):value;
    }

    @Override
    public boolean equals(Object o) {
        if (this==o) return true;
        if (o==null || getClass()!=o.getClass()) return false;
        YearValueObject that = (YearValueObject) o;
        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return this.value();
    }

    public String value() {return value;}
}
