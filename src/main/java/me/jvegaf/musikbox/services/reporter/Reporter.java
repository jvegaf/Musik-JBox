package me.jvegaf.musikbox.services.reporter;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Reporter {

    private final IntegerProperty processedItemsProperty;
    private final IntegerProperty totalItemsProperty;
    private final StringProperty statusProperty;

    public Reporter() {
        this.statusProperty = new SimpleStringProperty("");
        this.processedItemsProperty = new SimpleIntegerProperty(0);
        this.totalItemsProperty = new SimpleIntegerProperty(0);

        this.processedItemsProperty.addListener((observable, oldValue, newValue) -> {
            if (newValue.intValue() > 0)
                this.statusProperty.set("Processing " + newValue.intValue() + " of " + totalItemsProperty.get() + " " +
                                                "items");
        });

    }

    public void itemDispatched() {
        this.processedItemsProperty.set(this.processedItemsProperty.get() + 1);
        if (this.processedItemsProperty.get() == totalItemsProperty.get())
            setTotalItems(0);
        this.processedItemsProperty.set(0);
    }

    public void setTotalItems(int totalItems) {
        this.totalItemsProperty.set(totalItems);
    }

    public StringProperty StatusProperty() {
        return this.statusProperty;
    }
}
