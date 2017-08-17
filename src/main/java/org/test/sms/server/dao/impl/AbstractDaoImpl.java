package org.test.sms.server.dao.impl;

import org.jboss.logging.Logger;
import org.test.sms.common.entity.AppEntity;
import org.test.sms.common.enums.general.ErrorCode;
import org.test.sms.common.exception.AppException;
import org.test.sms.common.filter.AbstractFilter;
import org.test.sms.server.dao.AbstractDao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.lang.reflect.ParameterizedType;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public abstract class AbstractDaoImpl<T extends AppEntity> implements AbstractDao<T> {

    protected Logger logger = Logger.getLogger(getClass());

    @PersistenceContext
    protected EntityManager em;

    private Class<T> entityClass;

    private String entityClassName;

    @SuppressWarnings("unchecked")
    protected AbstractDaoImpl() {
        entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        entityClassName = entityClass.getSimpleName();
    }

    @Override
    public T add(T entity) throws AppException {
        LocalDateTime now = LocalDateTime.now();

        entity.setCreationTime(now);
        entity.setLastModifiedTime(now);

        em.persist(entity);

        return entity;
    }

    @Override
    public T update(T entity) throws AppException {
        entity.setLastModifiedTime(LocalDateTime.now());

        try {
            return em.merge(entity);
        } catch (OptimisticLockException e) {
            throw new AppException(ErrorCode.OBJECT_CHANGED);
        }
    }

    @Override
    public void delete(long id) throws AppException {
        load(id).ifPresent(entity -> em.remove(entity));
    }

    private Optional<T> load(long id) {
        TypedQuery<T> query = em.createQuery("SELECT new" + entityClassName + "(id) FROM " + entityClassName + " WHERE id = :id", entityClass);
        query.setParameter("id", id);

        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<T> get(long id) {
        return Optional.ofNullable(em.find(entityClass, id));
    }

    @Override
    public long getCount(AbstractFilter filter) {
        StringBuilder queryBuilder = new StringBuilder("SELECT COUNT(id) FROM " + entityClassName);
        Map<String, Object> params = new HashMap<>();

        if (Objects.nonNull(filter)) {
            queryBuilder.append(" WHERE 1 = 1");
            addFilter(queryBuilder, params, filter);
        }

        TypedQuery<Long> query = em.createQuery(queryBuilder.toString(), Long.class);
        params.keySet().forEach(e -> query.setParameter(e, params.get(e)));

        return query.getSingleResult();
    }

    @Override
    public List<T> getList(AbstractFilter filter) {
        StringBuilder queryBuilder = new StringBuilder("SELECT new" + entityClassName + "(" + getSelect() + ") FROM " + entityClassName);
        Map<String, Object> params = new HashMap<>();

        if (Objects.nonNull(filter)) {
            queryBuilder.append(" WHERE 1 = 1");
            addFilter(queryBuilder, params, filter);
        }

        queryBuilder.append(" ORDER BY ").append(getOrderBy());

        TypedQuery<T> query = em.createQuery(queryBuilder.toString(), entityClass);
        params.keySet().forEach(e -> query.setParameter(e, params.get(e)));

        return query.getResultList();
    }

    protected abstract String getSelect();

    protected abstract void addFilter(StringBuilder queryBuilder, Map<String, Object> params, AbstractFilter abstractFilter);

    protected abstract String getOrderBy();
}