package me.jvegaf.musikbox.context.tracks.application.upgrade;

import me.jvegaf.musikbox.shared.domain.bus.command.CommandHandler;

public class UpgradeTrackCommandHandler implements CommandHandler<UpgradeTrackCommand> {

    private final TrackUpgrader upgrader;

    public UpgradeTrackCommandHandler(TrackUpgrader upgrader) {
        this.upgrader = upgrader;
    }

    @Override public void handle(UpgradeTrackCommand command) {

        upgrader.upgrade(command.track());
    }
}
