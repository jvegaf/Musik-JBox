package me.jvegaf.musikbox.app.collection;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.extern.log4j.Log4j2;
import me.jvegaf.musikbox.app.items.Category;
import me.jvegaf.musikbox.context.playlists.application.PlaylistResponse;
import me.jvegaf.musikbox.context.playlists.application.find.FindPlaylistQuery;
import me.jvegaf.musikbox.context.trackplaylist.application.search.SearchAllTracksInPlaylistQuery;
import me.jvegaf.musikbox.context.tracks.application.TracksResponse;
import me.jvegaf.musikbox.context.tracks.application.find.FindTrackQuery;
import me.jvegaf.musikbox.context.tracks.application.search_all.SearchAllTracksQuery;
import me.jvegaf.musikbox.shared.domain.Service;
import me.jvegaf.musikbox.shared.domain.TrackResponse;
import me.jvegaf.musikbox.shared.domain.bus.event.DomainEvent;
import me.jvegaf.musikbox.shared.domain.bus.event.DomainEventSubscriber;
import me.jvegaf.musikbox.shared.domain.bus.query.QueryBus;
import me.jvegaf.musikbox.shared.domain.track.TrackCreatedDomainEvent;
import me.jvegaf.musikbox.shared.domain.track.TrackUpdatedDomainEvent;
import org.springframework.context.event.EventListener;

@Log4j2
@Service
@DomainEventSubscriber({DomainEvent.class})
public final class MusicCollection {

    private final ObservableList<TrackResponse> tracks;
    private final QueryBus                      bus;
    private final ObjectProperty<Category>      collectionCategory;
    private final StringProperty                playListName;
    private final IntegerProperty               collectionTracksCount;
    private String playlistID = null;


    public MusicCollection(QueryBus bus) {
        this.bus                   = bus;
        this.tracks                = FXCollections.observableArrayList(libraryTracksRequest().tracks());
        this.collectionCategory    = new SimpleObjectProperty<>(Category.HEAD);
        this.playListName          = new SimpleStringProperty("");
        this.collectionTracksCount = new SimpleIntegerProperty(tracks.size());
    }

    private TracksResponse libraryTracksRequest() {
        return (TracksResponse) bus.ask(new SearchAllTracksQuery());
    }

    @EventListener
    public void on(DomainEvent event) {
        if (event instanceof TrackCreatedDomainEvent && collectionCategory.get()==Category.HEAD) {
            getNewTrack(event.aggregateId());
            log.info("added to collection: " + event.aggregateId());
        }

        if (event instanceof TrackUpdatedDomainEvent) {
            if (playlistID != null){
                requestTracksOnPlaylist(playlistID);
                return;
            }
            requestTracksOnLibrary();
        }
    }

    private void getNewTrack(String aggregateId) {
        TrackResponse tr = (TrackResponse) bus.ask(new FindTrackQuery(aggregateId));
        tracks.add(tr);
    }

    public void onSelectionChange(Category type, String selectedId) {
        switch (type) {
            case HEAD -> requestTracksOnLibrary();
            case PLAYLIST -> requestTracksOnPlaylist(selectedId);
        }
    }

    private void requestTracksOnLibrary() {
        playlistID = null;
        var response = libraryTracksRequest();
        collectionCategory.set(Category.HEAD);
        playListName.set("");
        tracks.clear();
        tracks.addAll(response.tracks());
        collectionTracksCount.set(response.tracks().size());
    }

    private void requestTracksOnPlaylist(String playlistId) {
        playlistID = playlistId;
        var response = tracksOfPlaylistRequest(playlistId);
        collectionCategory.set(Category.PLAYLIST);
        playListName.set(playListName(playlistId));
        tracks.clear();
        tracks.addAll(response.tracks());
        collectionTracksCount.set(response.tracks().size());
    }

    private TracksResponse tracksOfPlaylistRequest(String selectedId) {
        return (TracksResponse) bus.ask(new SearchAllTracksInPlaylistQuery(selectedId));
    }

    public String playListName(String selectedId) {
        var response = (PlaylistResponse) bus.ask(new FindPlaylistQuery(selectedId));
        return response.name();
    }

    public ObservableList<TrackResponse> tracksProperty() {
        return tracks;
    }

    public ObjectProperty<Category> collectionCategoryProperty() {return collectionCategory;}

    public StringProperty playListNameProperty() {return playListName;}

    public IntegerProperty collectionTracksCountProperty() {return collectionTracksCount;}

    public int getSize() {
        return tracks.size();
    }

    public Category getCategory() {
        return collectionCategory.get();
    }
}
