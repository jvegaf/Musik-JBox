package com.github.jvegaf.musikbox.shared.infrastructure.persistence.hibernate;

import com.github.jvegaf.musikbox.shared.domain.Identifier;
import com.github.jvegaf.musikbox.shared.domain.criteria.Criteria;
import org.hibernate.SessionFactory;

import javax.persistence.criteria.CriteriaQuery;
import java.util.List;
import java.util.Optional;

public abstract class HibernateRepository<T> {
    protected final SessionFactory             sessionFactory;
    protected final Class<T>                   aggregateClass;
    protected final HibernateCriteriaConverter<T> criteriaConverter;

    public HibernateRepository(SessionFactory sessionFactory, Class<T> aggregateClass) {
        this.sessionFactory    = sessionFactory;
        this.aggregateClass    = aggregateClass;
        this.criteriaConverter = new HibernateCriteriaConverter<T>(sessionFactory.getCriteriaBuilder());
    }

    protected void persist(T entity) {
        sessionFactory.getCurrentSession()
                      .saveOrUpdate(entity);
        sessionFactory.getCurrentSession()
                      .flush();
        sessionFactory.getCurrentSession()
                      .clear();
    }

    protected void remove(T entity) {
        sessionFactory.getCurrentSession()
                      .delete(entity);
        sessionFactory.getCurrentSession()
                      .flush();
        sessionFactory.getCurrentSession()
                      .clear();
    }

    protected Optional<T> byId(Identifier id) {
        return Optional.ofNullable(sessionFactory.getCurrentSession()
                                                 .byId(aggregateClass)
                                                 .load(id));
    }

    protected List<T> byCriteria(Criteria criteria) {
        CriteriaQuery<T> hibernateCriteria = criteriaConverter.convert(criteria, aggregateClass);

        return sessionFactory.getCurrentSession()
                             .createQuery(hibernateCriteria)
                             .getResultList();
    }

    protected List<T> all() {
        CriteriaQuery<T>
                criteria =
                sessionFactory.getCriteriaBuilder()
                              .createQuery(aggregateClass);

        criteria.from(aggregateClass);

        return sessionFactory.getCurrentSession()
                             .createQuery(criteria)
                             .getResultList();
    }
}
