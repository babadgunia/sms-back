package org.test.sms.server.dao.impl.university;

import org.springframework.stereotype.Repository;
import org.test.sms.common.entity.university.Auditorium;
import org.test.sms.common.entity.university.Building;
import org.test.sms.common.filter.general.AbstractFilter;
import org.test.sms.common.filter.university.AuditoriumFilter;
import org.test.sms.server.dao.impl.general.AbstractDaoImpl;
import org.test.sms.server.dao.interfaces.university.AuditoriumDao;

import java.util.Map;
import java.util.Objects;

@Repository
public class AuditoriumDaoImpl extends AbstractDaoImpl<Auditorium> implements AuditoriumDao {

    @Override
    protected Auditorium init(Auditorium entity) {
        return entity;
    }

    @Override
    protected String getSelect() {
        return "id, name";
    }

    @Override
    protected void addFilter(StringBuilder queryBuilder, Map<String, Object> params, AbstractFilter abstractFilter) {
        AuditoriumFilter filter = (AuditoriumFilter) abstractFilter;

        Building building = filter.getBuilding();
        if (Objects.nonNull(building)) {
            queryBuilder.append(" AND building = :building");
            params.put("building", building);
        }
    }

    @Override
    protected String getOrderBy() {
        return "name";
    }
}