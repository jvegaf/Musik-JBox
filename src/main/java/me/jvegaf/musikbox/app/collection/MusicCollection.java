package me.jvegaf.musikbox.app.collection;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import me.jvegaf.musikbox.app.items.Category;
import me.jvegaf.musikbox.context.trackplaylist.application.search.SearchAllTracksInPlaylistQuery;
import me.jvegaf.musikbox.context.tracks.application.TrackResponse;
import me.jvegaf.musikbox.context.tracks.application.TracksResponse;
import me.jvegaf.musikbox.context.tracks.application.search_all.SearchAllTracksQuery;
import me.jvegaf.musikbox.shared.domain.Service;
import me.jvegaf.musikbox.shared.domain.bus.query.QueryBus;

import java.util.List;

@Service
public final class MusicCollection implements Collection {

    private final ObjectProperty<List<TrackResponse>> tracksProperty;
    private final QueryBus                            bus;


    public MusicCollection(QueryBus bus) {
        this.bus            = bus;
        this.tracksProperty = new SimpleObjectProperty<>(libraryTracksRequest().tracks());
    }

    @Override
    public void onSelectionChange(Category type, String selectedId) {

        TracksResponse response = null;

        switch (type) {
            case HEAD:
                response = libraryTracksRequest();
                break;
            case PLAYLIST:
                response = tracksOfPlaylistRequest(selectedId);
        }
        tracksProperty.set(response.tracks());
    }

    private TracksResponse libraryTracksRequest() {
        return (TracksResponse) bus.ask(new SearchAllTracksQuery());
    }

    private TracksResponse tracksOfPlaylistRequest(String selectedId) {
        return (TracksResponse) bus.ask(new SearchAllTracksInPlaylistQuery(selectedId));
    }

    @Override
    public List<TrackResponse> getTracks() {
        return tracksProperty.get();
    }

    @Override
    public ObjectProperty<List<TrackResponse>> tracksProperty() {
        return tracksProperty;
    }
}
