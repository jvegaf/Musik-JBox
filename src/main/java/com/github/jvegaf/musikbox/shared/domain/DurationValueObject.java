package com.github.jvegaf.musikbox.shared.domain;

public abstract class DurationValueObject {

    private final Integer value;

    public DurationValueObject(Integer value) {
        this.value = value;
    }

    public DurationValueObject(String value) {
        this.value = fromStringValue(value);
    }

    public DurationValueObject() {
        this.value = null;
    }

    private int fromStringValue(String strValue) {
        String[] timeParts = strValue.split(":");
        int      minutes   = Integer.parseInt(timeParts[0]);
        int      seconds   = Integer.parseInt(timeParts[1]);
        return minutes * 60 + seconds;
    }

    public Integer value() {return value;}

    public String stringValue() {
        int minutes = this.value / 60;
        int seconds = this.value % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }


}
