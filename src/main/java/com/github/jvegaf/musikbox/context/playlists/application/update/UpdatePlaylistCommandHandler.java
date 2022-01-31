package com.github.jvegaf.musikbox.context.playlists.application.update;

import com.github.jvegaf.musikbox.context.playlists.domain.PlaylistId;
import com.github.jvegaf.musikbox.context.playlists.domain.PlaylistName;
import com.github.jvegaf.musikbox.shared.domain.Service;
import com.github.jvegaf.musikbox.shared.domain.bus.command.CommandHandler;

@Service
public final class UpdatePlaylistCommandHandler implements CommandHandler<UpdatePlaylistCommand> {

    private final PlaylistUpdater updater;

    public UpdatePlaylistCommandHandler(PlaylistUpdater updater) {this.updater = updater;}

    @Override
    public void handle(UpdatePlaylistCommand command) {
        PlaylistId   id   = new PlaylistId(command.id());
        PlaylistName name = new PlaylistName(command.name());
        updater.update(id, name);
    }
}
