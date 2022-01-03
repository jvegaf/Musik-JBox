package me.jvegaf.musikbox.context.playlists.infrastructure.persistence;

import me.jvegaf.musikbox.context.playlists.domain.Playlist;
import me.jvegaf.musikbox.context.playlists.domain.PlaylistId;
import me.jvegaf.musikbox.context.playlists.domain.PlaylistRepository;
import me.jvegaf.musikbox.shared.domain.Service;
import me.jvegaf.musikbox.shared.domain.criteria.Criteria;
import me.jvegaf.musikbox.shared.infrastructure.persistence.hibernate.HibernateRepository;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional("musikbox-transaction_manager")
public class SQLitePlaylistRepository extends HibernateRepository<Playlist> implements PlaylistRepository {
    public SQLitePlaylistRepository(@Qualifier("musikbox-session_factory") SessionFactory sessionFactory) {
        super(sessionFactory, Playlist.class);
    }

    @Override
    public void save(Playlist Playlist) {
        persist(Playlist);
    }

    @Override
    public Optional<Playlist> search(PlaylistId id) {
        return byId(id);
    }

    @Override
    public List<Playlist> matching(Criteria criteria) {
        return byCriteria(criteria);
    }
}
