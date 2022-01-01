package me.jvegaf.musikbox.context.tracks.application.upgrade;

import me.jvegaf.musikbox.context.tracks.domain.*;
import me.jvegaf.musikbox.shared.domain.bus.command.CommandHandler;
import org.springframework.stereotype.Service;

@Service
public class UpgradeTrackCommandHandler implements CommandHandler<UpgradeTrackCommand> {

    private final TrackUpgrader upgrader;

    public UpgradeTrackCommandHandler(TrackUpgrader upgrader) {
        this.upgrader = upgrader;
    }

    @Override
    public void handle(UpgradeTrackCommand command) {

        TrackId Id = new TrackId(command.trackId());
        TrackTitle Title = new TrackTitle(command.title());
        TrackArtist Artist = new TrackArtist(command.artist());
        TrackAlbum Album = new TrackAlbum(command.album());
        TrackGenre Genre = new TrackGenre(command.genre());
        TrackYear Year = new TrackYear(command.year());
        TrackBpm Bpm = new TrackBpm(command.bpm());
        TrackInitKey InitKey = new TrackInitKey(command.key());
        TrackComments Comments= new TrackComments(command.comments());

        upgrader.upgrade(
                Id,
                Title,
                Artist,
                Album,
                Genre,
                Year,
                Bpm,
                InitKey,
                Comments
        );
    }
}
