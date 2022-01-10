package me.jvegaf.musikbox.context.playlists.application.update;

import me.jvegaf.musikbox.context.playlists.domain.PlaylistId;
import me.jvegaf.musikbox.context.playlists.domain.PlaylistName;
import me.jvegaf.musikbox.shared.domain.Service;
import me.jvegaf.musikbox.shared.domain.bus.command.CommandHandler;

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
