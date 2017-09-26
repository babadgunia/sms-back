package org.test.sms.server.dao.impl.general;

import org.springframework.stereotype.Repository;
import org.test.sms.common.entity.general.ActionLog;
import org.test.sms.common.filter.general.AbstractFilter;
import org.test.sms.server.dao.interfaces.general.ActionLogDao;

import java.util.Map;

@Repository
public class ActionLogDaoImpl extends AbstractDaoImpl<ActionLog> implements ActionLogDao {

    @Override
    protected ActionLog init(ActionLog entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected String getSelect() {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void addFilter(StringBuilder queryBuilder, Map<String, Object> params, AbstractFilter abstractFilter) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected String getOrderBy() {
        throw new UnsupportedOperationException();
    }
}