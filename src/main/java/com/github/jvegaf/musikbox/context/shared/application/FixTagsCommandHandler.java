package com.github.jvegaf.musikbox.context.shared.application;

import com.github.jvegaf.musikbox.context.shared.domain.Artwork;
import com.github.jvegaf.musikbox.context.tags.application.TagResponse;
import com.github.jvegaf.musikbox.context.tags.application.search.SearchTagsQuery;
import com.github.jvegaf.musikbox.context.tracks.application.update.UpdateTrackCommand;
import com.github.jvegaf.musikbox.shared.domain.Service;
import com.github.jvegaf.musikbox.shared.domain.bus.command.CommandBus;
import com.github.jvegaf.musikbox.shared.domain.bus.command.CommandHandler;
import com.github.jvegaf.musikbox.shared.domain.bus.query.QueryBus;
import com.github.jvegaf.musikbox.shared.infrastructure.PictureFetcher;
import org.jaudiotagger.tag.reference.PictureTypes;

import java.io.IOException;

@Service
public class FixTagsCommandHandler implements CommandHandler<FixTagsCommand> {

    private final QueryBus       queryBus;
    private final CommandBus     commmandBus;
    private final PictureFetcher picFetcher;

    public FixTagsCommandHandler(
            QueryBus queryBus, CommandBus commmandBus, PictureFetcher picFetcher
    ) {
        this.queryBus    = queryBus;
        this.commmandBus = commmandBus;
        this.picFetcher  = picFetcher;
    }


    @Override
    public void handle(FixTagsCommand command) {
        TagResponse r = (TagResponse) queryBus.ask(new SearchTagsQuery(command.track()
                                                                              .title(),
                                                                       command.track()
                                                                              .artist()
                                                                              .orElse(""),
                                                                       command.track()
                                                                              .durationInt()));

        if (r.tag()==null) return;


        if (r.tag()
             .artworkURL()!=null) {
            try {
                byte[] coverData = picFetcher.fetchFromURI(r.tag()
                                                            .artworkURL());
                String mimetype = picFetcher.getMimeType(r.tag()
                                                          .artworkURL());
                Integer pictureType = PictureTypes.DEFAULT_ID;
                Artwork art = new Artwork(coverData,
                                          "",
                                          r.tag()
                                           .artworkURL(),
                                          false,
                                          mimetype,
                                          pictureType);
                commmandBus.dispatch(new PersistArtworkCommand(art,
                                                               command.track()
                                                                      .location()));
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

        commmandBus.dispatch(new UpdateTrackCommand(command.track()
                                                           .id(),
                                                    r.tag()
                                                     .title(),
                                                    r.tag()
                                                     .artist(),
                                                    r.tag()
                                                     .album(),
                                                    r.tag()
                                                     .genre(),
                                                    r.tag()
                                                     .year(),
                                                    r.tag()
                                                     .bpm(),
                                                    r.tag()
                                                     .key(),
                                                    null));

    }
}
