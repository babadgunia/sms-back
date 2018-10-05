package org.test.sms.server.dao.impl.university;

import org.springframework.stereotype.Repository;
import org.test.sms.common.entity.university.Group;
import org.test.sms.common.filter.general.AbstractFilter;
import org.test.sms.server.dao.impl.general.AbstractDaoImpl;
import org.test.sms.server.dao.interfaces.university.GroupDao;

import java.util.Map;

@Repository
public class GroupDaoImpl extends AbstractDaoImpl<Group> implements GroupDao {

    @Override
    protected Group init(Group entity) {
        return entity;
    }

    @Override
    protected String getSelect() {
        return null;
    }

    @Override
    protected void addFilter(StringBuilder queryBuilder, Map<String, Object> params, AbstractFilter abstractFilter) {}

    @Override
    protected String getOrderBy() {
        return null;
    }
}