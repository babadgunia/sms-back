package org.test.sms.server.dao.interfaces.general;

import org.test.sms.common.entity.general.ExceptionLog;
import org.test.sms.common.exception.AppException;
import org.test.sms.server.dao.AbstractDao;

public interface ExceptionLogDao extends AbstractDao<ExceptionLog> {

    ExceptionLog add(Throwable t) throws AppException;
}