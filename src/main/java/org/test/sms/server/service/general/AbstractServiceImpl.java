package org.test.sms.server.service.general;

import org.test.sms.common.entity.general.AbstractEntity;
import org.test.sms.common.exception.AppException;
import org.test.sms.common.filter.general.AbstractFilter;
import org.test.sms.common.service.general.AbstractService;
import org.test.sms.server.dao.interfaces.general.AbstractDao;

import java.util.List;
import java.util.Optional;

public abstract class AbstractServiceImpl<T extends AbstractEntity> implements AbstractService<T> {

    protected AbstractDao<T> dao;

    protected AbstractServiceImpl(AbstractDao<T> dao) {
        this.dao = dao;
    }

    @Override
    public T add(T entity) throws AppException {
        processEntity(entity);
        validateSave(entity);

        T result = dao.add(entity);
        performAdditionalOperationsPostAdd(result);

        return result;
    }

    protected void processEntity(T entity) {}

    protected void validateSave(T entity) throws AppException {}

    protected void performAdditionalOperationsPostAdd(T entity) {}

    @Override
    public T update(T entity) throws AppException {
        processEntity(entity);
        validateSave(entity);

        return dao.update(entity);
    }

    @Override
    public void delete(long id) throws AppException {
        validateDelete(id);

        dao.delete(id);
    }

    protected void validateDelete(long id) throws AppException {}

    @Override
    public Optional<T> get(AbstractFilter filter) {
        return dao.get(filter);
    }

    @Override
    public long getCount(AbstractFilter filter) {
        return dao.getCount(filter);
    }

    @Override
    public List<T> getList(AbstractFilter filter) {
        return dao.getList(filter);
    }
}