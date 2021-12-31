package me.jvegaf.musikbox.context.tracks.infrastructure.file;

import lombok.extern.log4j.Log4j2;
import me.jvegaf.musikbox.shared.domain.bus.command.CommandHandler;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Optional;

@Log4j2
@Service
public class CollectFilesCommandHandler implements CommandHandler<CollectFilesCommand> {

    private final FileManager manager;

    public CollectFilesCommandHandler(FileManager manager) {
        this.manager = manager;
    }

    @Override
    public void handle(CollectFilesCommand command) {
        log.info("Collecting files from {}", command.path().getAbsolutePath());
        Optional<File[]> files = Optional.ofNullable(command.path().listFiles((dir, name) -> name.endsWith(".mp3")));
        files.ifPresent(manager::dispatchFiles);
    }
}
