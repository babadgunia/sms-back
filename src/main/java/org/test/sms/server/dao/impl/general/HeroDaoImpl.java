package org.test.sms.server.dao.impl.general;

import org.springframework.stereotype.Repository;
import org.test.sms.common.entity.general.Hero;
import org.test.sms.common.filter.AbstractFilter;
import org.test.sms.common.filter.general.HeroFilter;
import org.test.sms.server.dao.impl.AbstractDaoImpl;
import org.test.sms.server.dao.interfaces.general.HeroDao;

import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
public class HeroDaoImpl extends AbstractDaoImpl<Hero> implements HeroDao {

    @Override
    public List<Hero> getList(AbstractFilter filter) {
        StringBuilder queryBuilder = new StringBuilder("SELECT new Hero(id, name) FROM Hero WHERE 1 = 1");
        Map<String, Object> params = new HashMap<>();

        addFilter(queryBuilder, params, (HeroFilter) filter);

        queryBuilder.append(" ORDER BY name");

        TypedQuery<Hero> query = em.createQuery(queryBuilder.toString(), Hero.class);
        params.keySet().forEach(e -> query.setParameter(e, params.get(e)));

        return query.getResultList();
    }

    private void addFilter(StringBuilder queryBuilder, Map<String, Object> params, HeroFilter filter) {
        if (Objects.isNull(filter)) {
            return;
        }

        String name = filter.getName();
        if (Objects.nonNull(name)) {
            queryBuilder.append(" AND name LIKE :name");
            params.put("name", "%" + name + "%");
        }
    }
}