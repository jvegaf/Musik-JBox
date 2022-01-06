package me.jvegaf.musikbox.app.collection;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import me.jvegaf.musikbox.app.items.Category;
import me.jvegaf.musikbox.shared.domain.TrackResponse;

import java.util.List;

public interface Collection {

    void onSelectionChange(Category type, String selectedId);

    List<TrackResponse> getTracks();

    ObjectProperty<List<TrackResponse>> tracksProperty();

    ObjectProperty<Category> collectionCategoryProperty();

    StringProperty playListNameProperty();

    IntegerProperty collectionTracksCountProperty();
}
