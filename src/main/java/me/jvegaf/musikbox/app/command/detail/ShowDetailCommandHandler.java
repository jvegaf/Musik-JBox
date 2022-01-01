package me.jvegaf.musikbox.app.command.detail;

import me.jvegaf.musikbox.app.player.MusicPlayer;
import me.jvegaf.musikbox.context.tracks.application.TrackResponse;
import me.jvegaf.musikbox.context.tracks.application.find.FindTrackQuery;
import me.jvegaf.musikbox.shared.domain.bus.command.CommandHandler;
import me.jvegaf.musikbox.shared.domain.bus.query.QueryBus;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class ShowDetailCommandHandler implements CommandHandler<ShowDetailCommand> {


    private final Music

    public ShowDetailCommandHandler( QueryBus bus) {

        this.bus     = bus;
    }

    @Override
    public void handle(ShowDetailCommand command) {
        TrackResponse t = (TrackResponse) bus.ask(new FindTrackQuery(command.trackId()));
        player.playTrack(t);
    }
}
