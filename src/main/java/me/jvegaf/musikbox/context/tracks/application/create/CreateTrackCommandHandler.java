package me.jvegaf.musikbox.context.tracks.application.create;

import me.jvegaf.musikbox.context.tracks.domain.*;
import me.jvegaf.musikbox.shared.domain.bus.command.CommandHandler;
import org.springframework.stereotype.Service;

@Service
public class CreateTrackCommandHandler implements CommandHandler<CreateTrackCommand> {

    private final TrackCreator creator;

    public CreateTrackCommandHandler(TrackCreator creator) {
        this.creator = creator;
    }

    @Override
    public void handle(CreateTrackCommand command) {
        TrackTitle title = new TrackTitle(command.title());
        TrackLocation location = new TrackLocation(command.location());
        TrackDuration duration = new TrackDuration(command.duration());
        TrackArtist artist = new TrackArtist(command.artist());
        TrackAlbum album = new TrackAlbum(command.album());
        TrackGenre genre = new TrackGenre(command.genre());
        TrackYear year = new TrackYear(command.year());
        TrackComments comments = new TrackComments(command.comments());
        TrackBpm bpm = new TrackBpm(command.bpm());
        TrackInitKey key = new TrackInitKey(command.key());

        creator.create(
                title,
                location,
                duration,
                artist,
                album,
                genre,
                year,
                bpm,
                key,
                comments
        );
    }
}
