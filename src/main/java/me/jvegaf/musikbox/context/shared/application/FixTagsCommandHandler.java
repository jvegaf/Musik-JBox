package me.jvegaf.musikbox.context.shared.application;

import me.jvegaf.musikbox.context.tags.application.TagResponse;
import me.jvegaf.musikbox.context.tags.application.search.SearchTagsQuery;
import me.jvegaf.musikbox.context.tracks.application.update.UpdateTrackCommand;
import me.jvegaf.musikbox.shared.domain.Service;
import me.jvegaf.musikbox.shared.domain.bus.command.CommandBus;
import me.jvegaf.musikbox.shared.domain.bus.command.CommandHandler;
import me.jvegaf.musikbox.shared.domain.bus.query.QueryBus;

@Service
public class FixTagsCommandHandler implements CommandHandler<FixTagsCommand> {

    private final QueryBus   queryBus;
    private final CommandBus commmandBus;

    public FixTagsCommandHandler(QueryBus queryBus, CommandBus commmandBus) {
        this.queryBus    = queryBus;
        this.commmandBus = commmandBus;
    }


    @Override
    public void handle(FixTagsCommand command) {
        TagResponse r = (TagResponse) queryBus.ask(new SearchTagsQuery(command.track().title(),
                                                                       command.track().artist().orElse(""),
                                                                       command.track().durationInt()));

        if (r.tag()==null) return;

        commmandBus.dispatch(new UpdateTrackCommand(command.track().id(),
                                                    r.tag().title(),
                                                    r.tag().artist(),
                                                    r.tag().album(),
                                                    r.tag().genre(),
                                                    r.tag().year(),
                                                    r.tag().bpm(),
                                                    r.tag().key(),
                                                    null

        ));

    }
}
