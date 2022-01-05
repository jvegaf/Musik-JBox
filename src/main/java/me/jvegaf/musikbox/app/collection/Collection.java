package me.jvegaf.musikbox.app.collection;

import javafx.beans.property.ObjectProperty;
import me.jvegaf.musikbox.app.items.Category;
import me.jvegaf.musikbox.context.tracks.application.TrackResponse;

import java.util.List;

public interface Collection {

    void onSelectionChange(Category type, String selectedId);

    List<TrackResponse> getTracks();

    ObjectProperty<List<TrackResponse>> tracksProperty();
}
