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

    @Override public void handle(UpgradeTrackCommand command) {

        Track updatedTrack = command.track().improveMetadata(
                new TrackTitle(command.title()),
                new TrackArtist(command.artist()),
                new TrackAlbum(command.album()),
                new TrackGenre(command.genre()),
                new TrackYear(command.year()),
                new TrackBpm(command.bpm()),
                new TrackInitKey(command.key())
        );
        upgrader.upgrade(updatedTrack);
    }
}
