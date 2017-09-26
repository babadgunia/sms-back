package org.test.sms.server.dao.impl.general;

import org.springframework.stereotype.Repository;
import org.test.sms.common.entity.general.ExceptionLog;
import org.test.sms.common.filter.general.AbstractFilter;
import org.test.sms.server.dao.interfaces.general.ExceptionLogDao;

import java.util.Map;

@Repository
public class ExceptionLogDaoImpl extends AbstractDaoImpl<ExceptionLog> implements ExceptionLogDao {

    @Override
    protected ExceptionLog init(ExceptionLog entity) {
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