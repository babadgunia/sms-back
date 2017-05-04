package org.test.sms.server.dao;

import org.test.sms.common.entities.AppEntity;
import org.test.sms.common.exception.AppException;
import org.test.sms.common.filters.AbstractFilter;

import java.util.List;
import java.util.Optional;

public interface AbstractDao<T extends AppEntity> {

    T add(T entity) throws AppException;

    T update(T entity) throws AppException;

    void delete(long id) throws AppException;

    Optional<T> get(long id);

    List<T> getList(AbstractFilter filter);
}