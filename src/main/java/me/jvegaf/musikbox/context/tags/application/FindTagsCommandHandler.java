package me.jvegaf.musikbox.context.tags.application;

import me.jvegaf.musikbox.context.tags.domain.Tagger;
import me.jvegaf.musikbox.shared.domain.Service;
import me.jvegaf.musikbox.shared.domain.bus.command.CommandHandler;

@Service
public class FindTagsCommandHandler implements CommandHandler<FindTagsCommand> {

    private final Tagger tagger;

    public FindTagsCommandHandler(Tagger tagger) {
        this.tagger = tagger;
    }

    @Override
    public void handle(FindTagsCommand command) {

        tagger.search(command.title(), command.artist());
    }
}
