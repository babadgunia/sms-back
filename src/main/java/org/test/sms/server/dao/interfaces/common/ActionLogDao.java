package org.test.sms.server.dao.interfaces.common;

import org.test.sms.common.entities.ActionLog;
import org.test.sms.common.enums.ActionType;
import org.test.sms.common.exception.AppException;
import org.test.sms.server.dao.AbstractDao;

public interface ActionLogDao extends AbstractDao<ActionLog> {

    ActionLog add(ActionType type, String info, String username, String ipAddress) throws AppException;
}