package me.jvegaf.musikbox.context.tracks.application.create;

import me.jvegaf.musikbox.context.tracks.domain.TrackDuration;
import me.jvegaf.musikbox.context.tracks.domain.TrackId;
import me.jvegaf.musikbox.context.tracks.domain.TrackLocation;
import me.jvegaf.musikbox.context.tracks.domain.TrackTitle;
import me.jvegaf.musikbox.shared.domain.bus.command.CommandHandler;
import org.springframework.stereotype.Service;

@Service
public class CreateTrackCommandHandler implements CommandHandler<CreateTrackCommand> {

    private final TrackCreator creator;

    public CreateTrackCommandHandler(TrackCreator creator) {
        this.creator = creator;
    }

    @Override
    public void handle(CreateTrackCommand command) {
        TrackId id = TrackId.create();
        TrackTitle title = new TrackTitle(command.title());
        TrackLocation location = new TrackLocation(command.location());
        TrackDuration duration = new TrackDuration(command.duration());

        creator.create(id, title, location, duration);
    }
}
