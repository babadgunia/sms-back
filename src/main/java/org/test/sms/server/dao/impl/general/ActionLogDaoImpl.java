package org.test.sms.server.dao.impl.general;

import org.springframework.stereotype.Repository;
import org.test.sms.common.entities.general.ActionLog;
import org.test.sms.common.enums.ActionType;
import org.test.sms.common.exception.AppException;
import org.test.sms.common.filters.AbstractFilter;
import org.test.sms.server.dao.impl.AbstractDaoImpl;
import org.test.sms.server.dao.interfaces.general.ActionLogDao;

import java.util.List;
import java.util.Optional;

@Repository
public class ActionLogDaoImpl extends AbstractDaoImpl<ActionLog> implements ActionLogDao {

    @Override
    public ActionLog update(ActionLog entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<ActionLog> get(long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<ActionLog> getList(AbstractFilter filter) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ActionLog add(ActionType type, String info, String username, String ipAddress) throws AppException {
        ActionLog actionLog = new ActionLog();

        actionLog.setType(type);
        actionLog.setInfo(info);
        actionLog.setUsername(username);
        actionLog.setIpAddress(ipAddress);

        return add(actionLog);
    }
}