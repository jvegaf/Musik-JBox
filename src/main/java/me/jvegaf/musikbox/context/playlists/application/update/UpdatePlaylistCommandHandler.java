package me.jvegaf.musikbox.context.playlists.application.update;

import me.jvegaf.musikbox.context.playlists.domain.PlaylistId;
import me.jvegaf.musikbox.context.playlists.domain.PlaylistName;
import me.jvegaf.musikbox.shared.domain.bus.command.CommandHandler;
import org.springframework.stereotype.Service;

@Service
public final class UpdatePlaylistCommandHandler implements CommandHandler<UpdatePlaylistCommand> {

    private final PlaylistUpdater updater;

    public UpdatePlaylistCommandHandler(PlaylistUpdater updater) { this.updater = updater; }

    @Override
    public void handle(UpdatePlaylistCommand command) {
        PlaylistId id = new PlaylistId(command.id());
        PlaylistName name = new PlaylistName(command.name());
        updater.update(id, name);
    }
}
