package org.test.sms.server.dao.impl.university;

import org.springframework.stereotype.Repository;
import org.test.sms.common.entity.university.Group;
import org.test.sms.common.filter.AbstractFilter;
import org.test.sms.server.dao.impl.AbstractDaoImpl;
import org.test.sms.server.dao.interfaces.university.GroupDao;

import java.util.List;
import java.util.Map;

@Repository
public class GroupDaoImpl extends AbstractDaoImpl<Group> implements GroupDao {

    @Override
    public List<Group> getList(AbstractFilter filter) {
        return null;
    }

    @Override
    protected void addFilter(StringBuilder queryBuilder, Map<String, Object> params, AbstractFilter abstractFilter) {}
}