package org.test.sms.server.dao.impl.general;

import org.springframework.stereotype.Repository;
import org.test.sms.common.entity.general.Hero;
import org.test.sms.common.filter.AbstractFilter;
import org.test.sms.common.filter.general.HeroFilter;
import org.test.sms.server.dao.impl.AbstractDaoImpl;
import org.test.sms.server.dao.interfaces.general.HeroDao;

import java.util.Map;
import java.util.Objects;

@Repository
public class HeroDaoImpl extends AbstractDaoImpl<Hero> implements HeroDao {

    @Override
    protected String getSelect() {
        return "id, name";
    }

    @Override
    protected void addFilter(StringBuilder queryBuilder, Map<String, Object> params, AbstractFilter abstractFilter) {
        HeroFilter filter = (HeroFilter) abstractFilter;

        String name = filter.getName();
        if (Objects.nonNull(name)) {
            queryBuilder.append(" AND name LIKE :name");
            params.put("name", "%" + name + "%");
        }
    }

    @Override
    protected String getOrderBy() {
        return "name";
    }
}