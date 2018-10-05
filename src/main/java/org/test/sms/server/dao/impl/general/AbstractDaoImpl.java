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

    protected AbstractDaoImpl() {
        entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        entityClassName = entityClass.getSimpleName();
    }

    @Override
    public T add(T entity) {
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
    public void delete(long id) {
        load(id).ifPresent(entity -> em.remove(entity));
    }

    private Optional<T> load(long id) {
        return Optional.ofNullable(em.find(entityClass, id));
    }

    @Override
    public Optional<T> get(long id) {
        Optional<T> result = load(id);

        result.ifPresent(this::initLazyFields);

        return result;
    }

    protected void initLazyFields(T entity) {}

    @Override
    public long getCount(AbstractFilter filter) {
        StringBuilder queryBuilder = new StringBuilder("SELECT COUNT(id) FROM " + entityClassName);
        Map<String, Object> params = new HashMap<>();

        if (Objects.nonNull(filter)) {
            queryBuilder.append(" WHERE 1 = 1");
            addFilter(queryBuilder, params, filter);
        }

        TypedQuery<Long> query = em.createQuery(queryBuilder.toString(), Long.class);
        params.keySet().forEach(key -> query.setParameter(key, params.get(key)));

        return query.getSingleResult();
    }

    protected void addFilter(StringBuilder queryBuilder, Map<String, Object> params, AbstractFilter abstractFilter) {}

    @Override
    public List<T> getList(AbstractFilter filter) {
        StringBuilder queryBuilder = new StringBuilder(getSelect(filter) + "FROM " + entityClassName);
        Map<String, Object> params = new HashMap<>();

        if (Objects.nonNull(filter)) {
            queryBuilder.append(" WHERE 1 = 1");
            addFilter(queryBuilder, params, filter);

            getOrderBy(filter, queryBuilder);
        }

        TypedQuery<T> query = em.createQuery(queryBuilder.toString(), entityClass);
        params.keySet().forEach(key -> query.setParameter(key, params.get(key)));

        addPaging(filter, query);

        List<T> result = query.getResultList();
        initLazyFields(filter, result);

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

    protected void initLazyFields(AbstractFilter abstractFilter, List<T> entities) {}
}