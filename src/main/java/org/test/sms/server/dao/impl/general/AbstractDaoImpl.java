package org.test.sms.server.dao.impl.general;

import org.test.sms.common.entity.general.AbstractEntity;
import org.test.sms.common.enums.general.ErrorCodeType;
import org.test.sms.common.exception.AppException;
import org.test.sms.common.filter.general.AbstractFilter;
import org.test.sms.server.dao.interfaces.general.AbstractDao;

import javax.persistence.EntityManager;
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

public abstract class AbstractDaoImpl<T extends AbstractEntity> implements AbstractDao<T> {

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

        initSubEntities(entity, now, true);

        em.persist(entity);

        return entity;
    }

    protected void initSubEntities(T entity, LocalDateTime now, boolean isAdd) {}

    @Override
    public T update(T entity) throws AppException {
        LocalDateTime now = LocalDateTime.now();
        entity.setLastModifiedTime(now);
        initSubEntities(entity, now, false);

        try {
            return em.merge(entity);
        } catch (OptimisticLockException e) {
            throw new AppException(ErrorCodeType.OBJECT_CHANGED);
        }
    }

    @Override
    public void delete(long id) throws AppException {
        load(id).ifPresent(entity -> em.remove(entity));
    }

    private Optional<T> load(long id) {
        return Optional.ofNullable(em.find(entityClass, id));
    }

    @Override
    public Optional<T> get(long id) {
        Optional<T> result = load(id);

        result.ifPresent(this::init);

        return result;
    }

    @Override
    public long getCount(AbstractFilter filter) {
        StringBuilder queryBuilder = new StringBuilder("SELECT COUNT(id) FROM " + entityClassName + " e");
        Map<String, Object> params = new HashMap<>();

        if (Objects.nonNull(filter)) {
            queryBuilder.append(" WHERE 1 = 1");
            addFilter(queryBuilder, params, filter);
        }

        TypedQuery<Long> query = em.createQuery(queryBuilder.toString(), Long.class);
        params.keySet().forEach(key -> query.setParameter(key, params.get(key)));

        return query.getSingleResult();
    }

    @Override
    public List<T> getList(AbstractFilter filter) {
        StringBuilder queryBuilder = new StringBuilder("SELECT new " + entityClassName + "(" + getSelect() + ") FROM " + entityClassName + " e");
        Map<String, Object> params = new HashMap<>();

        if (Objects.nonNull(filter)) {
            queryBuilder.append(" WHERE 1 = 1");
            addFilter(queryBuilder, params, filter);
        }

        queryBuilder.append(" ORDER BY ").append(getOrderBy());

        TypedQuery<T> query = em.createQuery(queryBuilder.toString(), entityClass);
        params.keySet().forEach(key -> query.setParameter(key, params.get(key)));

        if (Objects.nonNull(filter)) {
            Integer offset = filter.getOffset();
            if (Objects.nonNull(offset)) {
                query.setFirstResult(offset);
            }

            Integer numRows = filter.getNumRows();
            if (Objects.nonNull(numRows)) {
                query.setMaxResults(numRows);
            }
        }

        return query.getResultList();
    }

    protected abstract T init(T entity);

    protected abstract String getSelect();

    protected abstract void addFilter(StringBuilder queryBuilder, Map<String, Object> params, AbstractFilter abstractFilter);

    protected abstract String getOrderBy();
}