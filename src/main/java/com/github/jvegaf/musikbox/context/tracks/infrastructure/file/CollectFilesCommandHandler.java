package com.github.jvegaf.musikbox.context.tracks.infrastructure.file;

import com.github.jvegaf.musikbox.shared.domain.Service;
import com.github.jvegaf.musikbox.shared.domain.bus.command.CommandHandler;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class CollectFilesCommandHandler implements CommandHandler<CollectFilesCommand> {

    private final FileManager manager;

    public CollectFilesCommandHandler(FileManager manager) {
        this.manager = manager;
    }

    @Override
    public void handle(CollectFilesCommand command) {
        log.info("Collecting files from {}",
                 command.path()
                        .getAbsolutePath());
        new Thread(() -> manager.dispatchFiles(command.path())).start();
    }
}
