package me.jvegaf.musikbox.app.command.player;

import me.jvegaf.musikbox.app.player.MusicPlayer;
import me.jvegaf.musikbox.context.tracks.application.TrackResponse;
import me.jvegaf.musikbox.context.tracks.application.find.FindTrackQuery;
import me.jvegaf.musikbox.shared.domain.Service;
import me.jvegaf.musikbox.shared.domain.bus.command.CommandHandler;
import me.jvegaf.musikbox.shared.domain.bus.query.QueryBus;

@Service
public class PlaybackCommandHandler implements CommandHandler<PlaybackCommand> {

    private final MusicPlayer player;
    private final QueryBus    bus;

    public PlaybackCommandHandler(MusicPlayer player, QueryBus bus) {
        this.player  = player;
        this.bus     = bus;
    }

    @Override
    public void handle(PlaybackCommand command) {
        TrackResponse t = (TrackResponse) bus.ask(new FindTrackQuery(command.trackId()));
        player.playTrack(t);
    }
}
