package me.jvegaf.musikbox.context.playlists.application.create;

import me.jvegaf.musikbox.context.playlists.domain.PlaylistName;
import me.jvegaf.musikbox.context.tracks.domain.TrackDuration;
import me.jvegaf.musikbox.context.tracks.domain.TrackId;
import me.jvegaf.musikbox.context.tracks.domain.TrackLocation;
import me.jvegaf.musikbox.context.tracks.domain.TrackTitle;
import me.jvegaf.musikbox.shared.domain.bus.command.CommandHandler;

public class CreatePlaylistCommandHandler implements CommandHandler<CreatePlaylistCommand> {

    private final PlaylistCreator creator;

    public CreatePlaylistCommandHandler(PlaylistCreator creator) {
        this.creator = creator;
    }

    @Override public void handle(CreatePlaylistCommand command) {
        PlaylistName name = new PlaylistName(command.name());

        creator.create(name);
    }
}
