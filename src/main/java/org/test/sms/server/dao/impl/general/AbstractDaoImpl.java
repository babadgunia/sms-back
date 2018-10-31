package org.test.sms.server.dao.impl.general;

import org.test.sms.common.entity.general.AbstractEntity;
import org.test.sms.common.enums.general.ErrorCodeType;
import org.test.sms.common.exception.AppException;
import org.test.sms.common.filter.general.AbstractFilter;
import org.test.sms.common.utils.Utils;
import org.test.sms.server.dao.interfaces.general.AbstractDao;

import javax.persistence.EntityManager;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.lang.reflect.ParameterizedType;
import java.time.ZonedDateTime;
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

    protected AbstractDaoImpl() {
        entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        entityClassName = entityClass.getSimpleName();
    }

    @Override
    public T add(T entity) {
        ZonedDateTime now = ZonedDateTime.now();

        entity.setCreated(now);
        entity.setLastModified(now);

        initSubEntities(entity, now, true);

        em.persist(entity);

        return entity;
    }

    protected void initSubEntities(T entity, ZonedDateTime now, boolean isAdd) {}

    @Override
    public T update(T entity) throws AppException {
        ZonedDateTime now = ZonedDateTime.now();
        entity.setLastModified(now);

        initSubEntities(entity, now, false);

        try {
            return em.merge(entity);
        } catch (OptimisticLockException e) {
            throw new AppException(ErrorCodeType.OBJECT_CHANGED);
        }
    }

    @Override
    public void delete(long id) {
        T entity = em.find(entityClass, id);
        if (Objects.nonNull(entity)) {
            em.remove(entity);
        }
    }

    @Override
    public Optional<T> get(AbstractFilter filter) {
        List<T> resultList = getList(filter);

        return !Utils.isBlank(resultList) ? Optional.of(resultList.get(0)) : Optional.empty();
    }

    @Override
    public long getCount(AbstractFilter filter) {
        StringBuilder queryBuilder = new StringBuilder("SELECT COUNT(id) FROM " + entityClassName);
        Map<String, Object> params = new HashMap<>();

        if (Objects.nonNull(filter)) {
            queryBuilder.append(" WHERE 1 = 1");
            addFilter(filter, queryBuilder, params);
        }

        TypedQuery<Long> query = em.createQuery(queryBuilder.toString(), Long.class);
        params.forEach(query::setParameter);

        return query.getSingleResult();
    }

    protected void addFilter(AbstractFilter abstractFilter, StringBuilder queryBuilder, Map<String, Object> params) {}

    @Override
    public List<T> getList(AbstractFilter filter) {
        StringBuilder queryBuilder = new StringBuilder(getSelect(filter) + "FROM " + entityClassName);
        Map<String, Object> params = new HashMap<>();

        if (Objects.nonNull(filter)) {
            queryBuilder.append(" WHERE 1 = 1");
            addFilter(filter, queryBuilder, params);

            getOrderBy(filter, queryBuilder);
        }

        TypedQuery<T> query = em.createQuery(queryBuilder.toString(), entityClass);
        params.forEach(query::setParameter);

        addPaging(filter, query);

        List<T> result = query.getResultList();

        if (filter != null) {
            for (T entity : result) {
                initLazyFields(filter, entity);
            }
        }

        return result;
    }

    private String getSelect(AbstractFilter filter) {
        if (filter != null) {
            String fields = filter.getFields();
            if (!Utils.isBlank(fields)) {
                return "SELECT new " + entityClassName + "(" + fields + ") ";
            }
        }

        return "";
    }

    private void getOrderBy(AbstractFilter filter, StringBuilder queryBuilder) {
        String orderBy = filter.getOrderBy();
        if (!Utils.isBlank(orderBy)) {
            queryBuilder.append(" ORDER BY ").append(orderBy);
        }
    }

    private void addPaging(AbstractFilter filter, Query query) {
        if (filter != null) {
            Integer offset = filter.getOffset();
            if (offset != null) {
                query.setFirstResult(offset - 1);
            }

            Integer numRows = filter.getNumRows();
            if (numRows != null) {
                query.setMaxResults(numRows);
            }
        }
    }

    protected void initLazyFields(AbstractFilter abstractFilter, T entity) {}
}