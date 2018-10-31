package org.test.sms.server.service.general;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.test.sms.common.entity.general.ExceptionLog;
import org.test.sms.common.exception.AppException;
import org.test.sms.common.service.general.ExceptionLogService;
import org.test.sms.common.utils.Utils;
import org.test.sms.server.dao.interfaces.general.ExceptionLogDao;

@Service
@Transactional
public class ExceptionLogServiceImpl extends AbstractServiceImpl<ExceptionLog> implements ExceptionLogService {

    @Autowired
    public ExceptionLogServiceImpl(ExceptionLogDao dao) {
        super(dao);
    }

    @Override
    public ExceptionLog add(Throwable t) throws AppException {
        ExceptionLog exceptionLog = new ExceptionLog();

        Throwable cause = Utils.findRelevantCause(t);
        exceptionLog.setInfo(cause.getClass().getName() + ": " + cause.getMessage());
        exceptionLog.setStackTrace(Utils.getStackTrace(t));

        return add(exceptionLog);
    }
}