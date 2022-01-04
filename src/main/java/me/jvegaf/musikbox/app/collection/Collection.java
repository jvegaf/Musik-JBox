package me.jvegaf.musikbox.app.collection;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import me.jvegaf.musikbox.app.items.Category;
import me.jvegaf.musikbox.context.tracks.application.TrackResponse;
import me.jvegaf.musikbox.context.tracks.application.TracksResponse;
import me.jvegaf.musikbox.context.tracks.application.search_all.SearchAllTracksQuery;
import me.jvegaf.musikbox.shared.domain.Service;
import me.jvegaf.musikbox.shared.domain.bus.query.QueryBus;

import java.util.List;

@Service
public final class Collection {

    private final ObjectProperty<List<TrackResponse>> tracksProperty;
    private final QueryBus                            bus;


    public Collection(QueryBus bus) {
        this.bus            = bus;
        this.tracksProperty = new SimpleObjectProperty<>();
    }

    public void onSelectionChange(Category type, String selectedId) {

        if (type == Category.HEAD) {
            TracksResponse response = (TracksResponse) bus.ask(new SearchAllTracksQuery());
            tracksProperty.set(response.tracks());
        }


    }

    public List<TrackResponse> getTracks() {
        return tracksProperty.get();
    }

    public ObjectProperty<List<TrackResponse>> tracksProperty() {
        return tracksProperty;
    }
}
