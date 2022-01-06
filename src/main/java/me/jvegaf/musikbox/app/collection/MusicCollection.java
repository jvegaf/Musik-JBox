package me.jvegaf.musikbox.app.collection;

import javafx.beans.property.*;
import me.jvegaf.musikbox.app.items.Category;
import me.jvegaf.musikbox.context.playlists.application.PlaylistResponse;
import me.jvegaf.musikbox.context.playlists.application.find.FindPlaylistQuery;
import me.jvegaf.musikbox.context.trackplaylist.application.search.SearchAllTracksInPlaylistQuery;
import me.jvegaf.musikbox.context.tracks.application.TracksResponse;
import me.jvegaf.musikbox.context.tracks.application.search_all.SearchAllTracksQuery;
import me.jvegaf.musikbox.shared.domain.Service;
import me.jvegaf.musikbox.shared.domain.TrackResponse;
import me.jvegaf.musikbox.shared.domain.bus.query.QueryBus;

import java.util.List;

@Service
public final class MusicCollection implements Collection {

    private final ObjectProperty<List<TrackResponse>> tracks;
    private final QueryBus                            bus;
    private final ObjectProperty<Category>            collectionCategory;
    private final StringProperty                      playListName;
    private final IntegerProperty                     collectionTracksCount;


    public MusicCollection(QueryBus bus) {
        this.bus                   = bus;
        this.tracks                = new SimpleObjectProperty<>(libraryTracksRequest().tracks());
        this.collectionCategory    = new SimpleObjectProperty<>(Category.HEAD);
        this.playListName          = new SimpleStringProperty("");
        this.collectionTracksCount = new SimpleIntegerProperty(tracks.get().size());
    }

    @Override
    public void onSelectionChange(Category type, String selectedId) {

        TracksResponse response = null;

        switch (type) {
            case HEAD:
                response = libraryTracksRequest();
                collectionCategory.set(Category.HEAD);
                playListName.set("");
                break;
            case PLAYLIST:
                response = tracksOfPlaylistRequest(selectedId);
                collectionCategory.set(Category.PLAYLIST);
                playListName.set(playListName(selectedId));
        }
        tracks.set(response.tracks());
        collectionTracksCount.set(response.tracks().size());
    }

    @Override
    public List<TrackResponse> getTracks() {
        return tracks.get();
    }

    private TracksResponse libraryTracksRequest() {
        return (TracksResponse) bus.ask(new SearchAllTracksQuery());
    }

    private TracksResponse tracksOfPlaylistRequest(String selectedId) {
        return (TracksResponse) bus.ask(new SearchAllTracksInPlaylistQuery(selectedId));
    }

    @Override
    public ObjectProperty<List<TrackResponse>> tracksProperty() {
        return tracks;
    }

    @Override
    public ObjectProperty<Category> collectionCategoryProperty() { return collectionCategory; }

    @Override
    public StringProperty playListNameProperty() { return playListName; }

    @Override
    public IntegerProperty collectionTracksCountProperty() { return collectionTracksCount; }

    private String playListName(String selectedId) {
        var response = (PlaylistResponse) bus.ask(new FindPlaylistQuery(selectedId));
        return response.name();
    }
}
