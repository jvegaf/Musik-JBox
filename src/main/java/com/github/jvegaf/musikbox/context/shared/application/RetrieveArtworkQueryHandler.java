package com.github.jvegaf.musikbox.context.shared.application;

import com.github.jvegaf.musikbox.context.shared.domain.Artwork;
import com.github.jvegaf.musikbox.context.shared.infrastructure.ArtworkRetriever;
import com.github.jvegaf.musikbox.shared.domain.Service;
import com.github.jvegaf.musikbox.shared.domain.bus.query.QueryHandler;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;

import java.io.IOException;

@Service
public class RetrieveArtworkQueryHandler implements QueryHandler<RetrieveArtworkQuery, Artwork> {

    private final ArtworkRetriever retriever;

    public RetrieveArtworkQueryHandler(ArtworkRetriever retriever) {this.retriever = retriever;}

    @Override
    public Artwork handle(RetrieveArtworkQuery query) {
        try {
            return retriever.retrieve(query.trackLocation());
        }
        catch (CannotReadException | TagException | InvalidAudioFrameException | ReadOnlyFileException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
