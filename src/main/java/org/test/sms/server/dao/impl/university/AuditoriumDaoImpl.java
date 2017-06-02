package org.test.sms.server.dao.impl.university;

import org.springframework.stereotype.Repository;
import org.test.sms.common.entity.university.Auditorium;
import org.test.sms.common.entity.university.Building;
import org.test.sms.common.filter.AbstractFilter;
import org.test.sms.common.filter.university.AuditoriumFilter;
import org.test.sms.server.dao.impl.AbstractDaoImpl;
import org.test.sms.server.dao.interfaces.university.AuditoriumDao;

import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
public class AuditoriumDaoImpl extends AbstractDaoImpl<Auditorium> implements AuditoriumDao {

    @Override
    public List<Auditorium> getList(AbstractFilter filter) {
        StringBuilder queryBuilder = new StringBuilder("SELECT new Auditorium(id, name) FROM Auditorium WHERE 1 = 1");
        Map<String, Object> params = new HashMap<>();

        addFilter(queryBuilder, params, (AuditoriumFilter) filter);

        queryBuilder.append(" ORDER BY name");

        TypedQuery<Auditorium> query = em.createQuery(queryBuilder.toString(), Auditorium.class);
        params.keySet().forEach(e -> query.setParameter(e, params.get(e)));

        return query.getResultList();
    }

    private void addFilter(StringBuilder queryBuilder, Map<String, Object> params, AuditoriumFilter filter) {
        if (Objects.isNull(filter)) {
            return;
        }

        Building building = filter.getBuilding();
        if (Objects.nonNull(building)) {
            queryBuilder.append(" AND building = :building");
            params.put("building", building);
        }
    }
}