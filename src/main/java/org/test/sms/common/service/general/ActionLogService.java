package org.test.sms.common.service.general;

import org.test.sms.common.entity.general.ActionLog;
import org.test.sms.common.enums.general.ActionType;
import org.test.sms.common.exception.AppException;
import org.test.sms.common.service.AbstractService;

public interface ActionLogService extends AbstractService<ActionLog> {

    ActionLog add(ActionType type, String info, String username, String ipAddress) throws AppException;
}