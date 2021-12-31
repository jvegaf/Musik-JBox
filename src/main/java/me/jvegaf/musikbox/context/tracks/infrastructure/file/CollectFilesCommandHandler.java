package me.jvegaf.musikbox.context.tracks.infrastructure.file;

import me.jvegaf.musikbox.context.tracks.domain.TrackDuration;
import me.jvegaf.musikbox.context.tracks.domain.TrackId;
import me.jvegaf.musikbox.context.tracks.domain.TrackLocation;
import me.jvegaf.musikbox.context.tracks.domain.TrackTitle;
import me.jvegaf.musikbox.shared.domain.bus.command.CommandHandler;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class CollectFilesCommandHandler implements CommandHandler<CollectFilesCommand> {

    private final FileManager manager;

    public CollectFilesCommandHandler(FileManager manager) {
        this.manager = manager;
    }

    @Override
    public void handle(CollectFilesCommand command) {
        File[] files = command.path().listFiles((dir, name) -> name.endsWith(".mp3"));
        assert files != null;
        manager.dispatchFiles(files);
    }
}
