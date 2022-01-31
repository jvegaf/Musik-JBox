package com.github.jvegaf.musikbox.context.trackplaylist.infrastructure.persistence;

import com.github.jvegaf.musikbox.context.trackplaylist.domain.TrackPlaylist;
import com.github.jvegaf.musikbox.context.trackplaylist.domain.TrackPlaylistId;
import com.github.jvegaf.musikbox.context.trackplaylist.domain.TrackPlaylistRepository;
import com.github.jvegaf.musikbox.shared.domain.Service;
import com.github.jvegaf.musikbox.shared.domain.criteria.Criteria;
import com.github.jvegaf.musikbox.shared.domain.criteria.Filter;
import com.github.jvegaf.musikbox.shared.domain.criteria.Filters;
import com.github.jvegaf.musikbox.shared.domain.criteria.Order;
import com.github.jvegaf.musikbox.shared.infrastructure.persistence.hibernate.HibernateRepository;
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
    public void save(TrackPlaylist trackPlaylist) {persist(trackPlaylist);}

    @Override
    public List<TrackPlaylist> search(String playlistId) {
        Filter   playlistFilter = Filter.create("playlistId", "contains", playlistId);
        Criteria criteria       = new Criteria(new Filters(List.of(playlistFilter)), Order.asc("position"));
        var      result         = byCriteria(new Criteria(new Filters(List.of(playlistFilter)), Order.none()));
        System.out.println(result.toString());
        return result;
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
    public void deleteAll(String playlistId) {
        List<TrackPlaylist> tracks = search(playlistId);
        tracks.forEach(this::delete);
    }
}
