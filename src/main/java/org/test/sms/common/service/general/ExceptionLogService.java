package org.test.sms.common.service.general;

import org.test.sms.common.entity.general.ExceptionLog;
import org.test.sms.common.exception.AppException;

public interface ExceptionLogService extends AbstractService<ExceptionLog> {

    ExceptionLog add(Throwable t) throws AppException;
}