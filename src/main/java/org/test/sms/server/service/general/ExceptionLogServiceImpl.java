package org.test.sms.server.service.general;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.test.sms.common.entities.general.ExceptionLog;
import org.test.sms.common.exception.AppException;
import org.test.sms.common.filters.AbstractFilter;
import org.test.sms.common.service.general.ExceptionLogService;
import org.test.sms.server.dao.interfaces.general.ExceptionLogDao;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ExceptionLogServiceImpl implements ExceptionLogService {

    private ExceptionLogDao dao;

    @Autowired
    public ExceptionLogServiceImpl(ExceptionLogDao dao) {
        this.dao = dao;
    }

    @Override
    public ExceptionLog add(ExceptionLog entity) throws AppException {
        return dao.add(entity);
    }

    @Override
    public ExceptionLog update(ExceptionLog entity) throws AppException {
        return dao.update(entity);
    }

    @Override
    public void delete(long id) throws AppException {
        dao.delete(id);
    }

    @Override
    public Optional<ExceptionLog> get(long id) {
        return dao.get(id);
    }

    @Override
    public List<ExceptionLog> getList(AbstractFilter filter) {
        return dao.getList(filter);
    }

    @Override
    public ExceptionLog add(Throwable t) throws AppException {
        return dao.add(t);
    }
}