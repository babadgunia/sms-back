package org.test.sms.server.dao.interfaces.common;

import org.test.sms.common.entities.ExceptionLog;
import org.test.sms.common.exception.AppException;
import org.test.sms.server.dao.AbstractDao;

public interface ExceptionLogDao extends AbstractDao<ExceptionLog> {

    ExceptionLog add(Throwable t) throws AppException;
}