package org.test.sms.server.dao.interfaces.general;

import org.test.sms.common.entities.general.ActionLog;
import org.test.sms.common.enums.general.ActionType;
import org.test.sms.common.exception.AppException;
import org.test.sms.server.dao.AbstractDao;

public interface ActionLogDao extends AbstractDao<ActionLog> {

    ActionLog add(ActionType type, String info, String username, String ipAddress) throws AppException;
}