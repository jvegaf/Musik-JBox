package me.jvegaf.musikbox.context.tracks.application.update;

import me.jvegaf.musikbox.context.tracks.domain.*;
import me.jvegaf.musikbox.shared.domain.bus.command.CommandHandler;
import org.springframework.stereotype.Service;

@Service
public class UpdateTrackCommandHandler implements CommandHandler<UpdateTrackCommand> {

    private final TrackUpdater updater;

    public UpdateTrackCommandHandler(TrackUpdater updater) {
        this.updater = updater;
    }

    @Override
    public void handle(UpdateTrackCommand command) {

        TrackId Id = new TrackId(command.trackId());
        TrackTitle Title = new TrackTitle(command.title());
        TrackArtist Artist = new TrackArtist(command.artist());
        TrackAlbum Album = new TrackAlbum(command.album());
        TrackGenre Genre = new TrackGenre(command.genre());
        TrackYear Year = new TrackYear(command.year());
        TrackBpm Bpm = new TrackBpm(command.bpm());
        TrackInitKey InitKey = new TrackInitKey(command.key());
        TrackComments Comments = new TrackComments(command.comments());

        updater.update(Id, Title, Artist, Album, Genre, Year, Bpm, InitKey, Comments);
    }
}
