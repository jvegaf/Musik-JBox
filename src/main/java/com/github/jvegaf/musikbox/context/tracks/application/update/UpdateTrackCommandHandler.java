package com.github.jvegaf.musikbox.context.tracks.application.update;

import com.github.jvegaf.musikbox.shared.domain.Service;
import com.github.jvegaf.musikbox.shared.domain.bus.command.CommandHandler;

@Service
public class UpdateTrackCommandHandler implements CommandHandler<UpdateTrackCommand> {

    private final TrackUpdater updater;

    public UpdateTrackCommandHandler(TrackUpdater updater) {
        this.updater = updater;
    }

    @Override
    public void handle(UpdateTrackCommand command) {

        updater.update(command);
    }
}
