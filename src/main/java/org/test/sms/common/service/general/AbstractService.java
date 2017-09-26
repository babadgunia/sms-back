package org.test.sms.common.service.general;

import org.test.sms.common.entity.general.AbstractEntity;
import org.test.sms.common.exception.AppException;
import org.test.sms.common.filter.general.AbstractFilter;

import java.util.List;
import java.util.Optional;

public interface AbstractService<T extends AbstractEntity> {

    T add(T entity) throws AppException;

    T update(T entity) throws AppException;

    void delete(long id) throws AppException;

    Optional<T> get(long id);

    long getCount(AbstractFilter filter);

    List<T> getList(AbstractFilter filter);
}