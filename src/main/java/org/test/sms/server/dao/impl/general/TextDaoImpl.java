package org.test.sms.server.dao.impl.general;

import org.springframework.stereotype.Repository;
import org.test.sms.common.entity.general.Text;
import org.test.sms.common.enums.general.ErrorCode;
import org.test.sms.common.exception.AppException;
import org.test.sms.common.filter.AbstractFilter;
import org.test.sms.common.utils.Utils;
import org.test.sms.server.dao.impl.AbstractDaoImpl;
import org.test.sms.server.dao.interfaces.general.TextDao;

import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class TextDaoImpl extends AbstractDaoImpl<Text> implements TextDao {

    @Override
    public Text add(Text entity) throws AppException {
        String key = entity.getKey();
        if (exists(key)) {
            throw new AppException(ErrorCode.TEXT_EXISTS, key);
        }

        return super.add(entity);
    }

    private boolean exists(String key) {
        TypedQuery<Text> query = em.createQuery("SELECT new Text(id) FROM Text WHERE key = :key", Text.class);
        query.setParameter("key", key);

        return !Utils.isBlank(query.getResultList());
    }

    @Override
    public Optional<Text> get(long id) {
        Optional<Text> result = super.get(id);

        result.ifPresent(e -> e.getValues().size());

        return result;
    }

    @Override
    public List<Text> getList(AbstractFilter filter) {
        TypedQuery<Text> query = em.createQuery("FROM Text ORDER BY key", Text.class);

        List<Text> result = query.getResultList();
        result.forEach(e -> e.getValues().size());

        return result;
    }
}