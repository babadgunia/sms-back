package org.test.sms.server.dao.impl;

import org.jboss.logging.Logger;
import org.test.sms.common.entities.AppEntity;
import org.test.sms.common.enums.ErrorCode;
import org.test.sms.common.exception.AppException;
import org.test.sms.common.filters.AbstractFilter;
import org.test.sms.server.dao.AbstractDao;

import javax.persistence.EntityManager;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;
import java.lang.reflect.ParameterizedType;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public abstract class AbstractDaoImpl<T extends AppEntity> implements AbstractDao<T> {

    protected Logger logger = Logger.getLogger(getClass());

    @PersistenceContext
    protected EntityManager em;

    private Class<T> entityClass;

    @SuppressWarnings("unchecked")
    protected AbstractDaoImpl() {
        entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
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
        get(id).ifPresent(e -> em.remove(e));
    }

    @Override
    public Optional<T> get(long id) {
        return Optional.ofNullable(em.find(entityClass, id));
    }

    @Override
    public List<T> getList(AbstractFilter filter) {
        return em.createQuery("FROM " + entityClass.getSimpleName(), entityClass).getResultList();
    }
}