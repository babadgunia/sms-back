package org.test.sms.server.service.general;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.test.sms.common.entity.general.ExceptionLog;
import org.test.sms.common.exception.AppException;
import org.test.sms.common.filter.general.AbstractFilter;
import org.test.sms.common.service.general.ExceptionLogService;
import org.test.sms.common.utils.Utils;
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
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(long id) throws AppException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<ExceptionLog> get(long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long getCount(AbstractFilter filter) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<ExceptionLog> getList(AbstractFilter filter) {
        throw new UnsupportedOperationException();
    }

//    misc

    @Override
    public ExceptionLog add(Throwable t) throws AppException {
        ExceptionLog exceptionLog = new ExceptionLog();

        Throwable cause = Utils.findRelevantCause(t);
        exceptionLog.setMessage(cause.getClass().getName() + ": " + cause.getMessage());
        exceptionLog.setStackTrace(Utils.getStackTrace(t));

        return add(exceptionLog);
    }
}