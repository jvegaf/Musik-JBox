package me.jvegaf.musikbox.context.playlists.application.create;

import me.jvegaf.musikbox.context.playlists.domain.PlaylistName;
import me.jvegaf.musikbox.shared.domain.bus.command.CommandHandler;
import org.springframework.stereotype.Service;

@Service
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
