package me.jvegaf.musikbox.context.trackplaylist.infrastucture.persistence;

import me.jvegaf.musikbox.context.playlists.domain.PlaylistId;
import me.jvegaf.musikbox.context.trackplaylist.domain.TrackPlaylist;
import me.jvegaf.musikbox.context.trackplaylist.domain.TrackPlaylistId;
import me.jvegaf.musikbox.context.trackplaylist.domain.TrackPlaylistRepository;
import me.jvegaf.musikbox.shared.domain.Service;
import me.jvegaf.musikbox.shared.domain.criteria.Criteria;
import me.jvegaf.musikbox.shared.domain.criteria.Filter;
import me.jvegaf.musikbox.shared.domain.criteria.Filters;
import me.jvegaf.musikbox.shared.domain.criteria.Order;
import me.jvegaf.musikbox.shared.infrastructure.persistence.hibernate.HibernateRepository;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional("musikbox-transaction_manager")
public class SQLiteTrackPlaylistRepository extends HibernateRepository<TrackPlaylist> implements TrackPlaylistRepository {

    public SQLiteTrackPlaylistRepository(@Qualifier("musikbox-session_factory") SessionFactory sessionFactory) {
        super(sessionFactory, TrackPlaylist.class);
    }

    @Override
    public void save(TrackPlaylist trackPlaylist) { persist(trackPlaylist); }

    @Override
    public List<TrackPlaylist> search(PlaylistId id) {
        Filter playlistFilter = Filter.create("playlistId", "contains", id.value());
        return byCriteria(new Criteria(new Filters(List.of(playlistFilter)), Order.none()));
    }

    @Override
    public Optional<TrackPlaylist> find(TrackPlaylistId id) {
        return byId(id);
    }

    @Override
    public void delete(TrackPlaylist trackPlaylist) {
        remove(trackPlaylist);
    }

    @Override
    public int freePosition(PlaylistId playlistId) {
        return search(playlistId).size();
    }

    @Override
    public void deleteAll(PlaylistId id) {
        List<TrackPlaylist> tracks = search(id);
        tracks.forEach(this::delete);
    }

}
