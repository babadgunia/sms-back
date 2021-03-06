package org.test.sms.server.dao.impl.general;

import org.springframework.stereotype.Repository;
import org.test.sms.common.entity.general.Text;
import org.test.sms.common.filter.general.AbstractFilter;
import org.test.sms.common.filter.general.TextFilter;
import org.test.sms.common.utils.Utils;
import org.test.sms.server.dao.interfaces.general.TextDao;

import javax.persistence.TypedQuery;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

@Repository
public class TextDaoImpl extends AbstractDaoImpl<Text> implements TextDao {

    @Override
    protected void initSubEntities(Text entity, ZonedDateTime now, boolean isAdd) {
        entity.getValues().forEach(value -> {
            if (isAdd) {
                value.setCreated(now);
            }
            value.setLastModified(now);
        });
    }

    @Override
    protected void initLazyFields(AbstractFilter abstractFilter, Text entity) {
        entity.getValues().size();
    }

    @Override
    protected void addFilter(AbstractFilter abstractFilter, StringBuilder queryBuilder, Map<String, Object> params) {
        TextFilter filter = (TextFilter) abstractFilter;

        String key = filter.getKey();
        if (!Utils.isBlank(key)) {
            queryBuilder.append(" AND key LIKE :key");
            params.put("key", "%" + key.toUpperCase() + "%");
        }

        String value = filter.getValue();
        if (!Utils.isBlank(value)) {
            queryBuilder.append(" AND EXISTS (SELECT new I18NText(id) FROM e.values WHERE UPPER(value) LIKE :value)");
            params.put("value", "%" + value.toUpperCase() + "%");
        }
    }

    @Override
    public boolean exists(String key) {
        TypedQuery<Text> query = em.createQuery("SELECT new Text(id) FROM Text WHERE (key) = :key", Text.class);
        query.setParameter("key", key.toUpperCase());

        return !Utils.isBlank(query.getResultList());
    }

    @Override
    public List<Text> getListForSelection() {
        return em.createQuery("SELECT new Text(id, key) FROM Text ORDER BY key", Text.class).getResultList();
    }
}