package org.test.sms.server.dao.impl.general;

import org.springframework.stereotype.Repository;
import org.test.sms.common.entity.general.ExceptionLog;
import org.test.sms.common.exception.AppException;
import org.test.sms.common.filter.AbstractFilter;
import org.test.sms.common.utils.Utils;
import org.test.sms.server.dao.impl.AbstractDaoImpl;
import org.test.sms.server.dao.interfaces.general.ExceptionLogDao;

import java.util.List;
import java.util.Optional;

@Repository
public class ExceptionLogDaoImpl extends AbstractDaoImpl<ExceptionLog> implements ExceptionLogDao {

    @Override
    public ExceptionLog update(ExceptionLog entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<ExceptionLog> get(long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<ExceptionLog> getList(AbstractFilter filter) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ExceptionLog add(Throwable t) throws AppException {
        ExceptionLog exceptionLog = new ExceptionLog();

        Throwable cause = Utils.findRelevantCause(t);
        exceptionLog.setMessage(cause.getClass().getName() + ": " + cause.getMessage());
        exceptionLog.setStackTrace(Utils.getStackTrace(t));

        return add(exceptionLog);
    }
}