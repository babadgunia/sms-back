package org.test.sms.server.dao.impl.general;

import org.springframework.stereotype.Repository;
import org.test.sms.common.entity.general.Permission;
import org.test.sms.common.entity.general.UserGroup;
import org.test.sms.common.enums.general.ErrorCode;
import org.test.sms.common.exception.AppException;
import org.test.sms.common.filter.AbstractFilter;
import org.test.sms.common.utils.Utils;
import org.test.sms.server.dao.impl.AbstractDaoImpl;
import org.test.sms.server.dao.interfaces.general.UserGroupDao;

import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class UserGroupDaoImpl extends AbstractDaoImpl<UserGroup> implements UserGroupDao {

    @Override
    public UserGroup add(UserGroup entity) throws AppException {
        String name = entity.getName();
        if (exists(name)) {
            throw new AppException(ErrorCode.USER_GROUP_EXISTS, name);
        }

        return super.add(entity);
    }

    private boolean exists(String name) {
        TypedQuery<UserGroup> query = em.createQuery("SELECT new UserGroup(id) FROM UserGroup WHERE UPPER(name) = :name", UserGroup.class);
        query.setParameter("name", name.toUpperCase());

        return !Utils.isBlank(query.getResultList());
    }

    @Override
    public Optional<UserGroup> get(long id) {
        Optional<UserGroup> result = super.get(id);

        result.ifPresent(e -> {
            List<Permission> permissions = e.getPermissions();
            permissions.size();
            permissions.forEach(f -> f.getPermissionTypes().size());
        });

        return result;
    }

    @Override
    protected UserGroup init(UserGroup entity) {
        return entity;
    }

    @Override
    protected String getSelect() {
        return "id, name";
    }

    @Override
    protected void addFilter(StringBuilder queryBuilder, Map<String, Object> params, AbstractFilter abstractFilter) {}

    @Override
    protected String getOrderBy() {
        return "name";
    }
}