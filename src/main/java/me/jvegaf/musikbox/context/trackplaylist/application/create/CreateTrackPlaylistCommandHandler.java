package me.jvegaf.musikbox.context.trackplaylist.application.create;

import me.jvegaf.musikbox.context.playlists.domain.PlaylistId;
import me.jvegaf.musikbox.context.tracks.domain.TrackId;
import me.jvegaf.musikbox.shared.domain.Service;
import me.jvegaf.musikbox.shared.domain.bus.command.CommandHandler;

@Service
public class CreateTrackPlaylistCommandHandler implements CommandHandler<CreateTrackPlaylistCommand> {

    private final TrackPlaylistCreator creator;

    public CreateTrackPlaylistCommandHandler(TrackPlaylistCreator creator) {
        this.creator = creator;
    }

    @Override
    public void handle(CreateTrackPlaylistCommand command) {

        PlaylistId playlistId = new PlaylistId(command.playlistId());
        TrackId trackId = new TrackId(command.trackId());

        creator.create(playlistId, trackId);
    }
}
