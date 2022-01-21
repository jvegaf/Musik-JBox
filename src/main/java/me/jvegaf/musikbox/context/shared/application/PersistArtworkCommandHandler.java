package me.jvegaf.musikbox.context.shared.application;

import me.jvegaf.musikbox.context.shared.infrastructure.ArtworkPersistor;
import me.jvegaf.musikbox.shared.domain.Service;
import me.jvegaf.musikbox.shared.domain.bus.command.CommandHandler;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.CannotWriteException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;

import java.io.IOException;

@Service
public class PersistArtworkCommandHandler implements CommandHandler<PersistArtworkCommand> {

    private final ArtworkPersistor persistor;

    public PersistArtworkCommandHandler(ArtworkPersistor persistor) {this.persistor = persistor;}

    @Override
    public void handle(PersistArtworkCommand command) {
        try {
            persistor.persist(command.trackLocation(), command.artwork());
        }
        catch (CannotReadException | CannotWriteException | IOException | ReadOnlyFileException | InvalidAudioFrameException | TagException e) {
            e.printStackTrace();
        }
    }
}
