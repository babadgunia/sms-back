package org.test.sms.common.service.general;

import org.test.sms.common.entities.general.ExceptionLog;
import org.test.sms.common.exception.AppException;
import org.test.sms.common.service.AbstractService;

public interface ExceptionLogService extends AbstractService<ExceptionLog> {

    ExceptionLog add(Throwable t) throws AppException;
}