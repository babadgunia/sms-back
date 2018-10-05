package org.test.sms.server.dao.impl.general;

import org.springframework.stereotype.Repository;
import org.test.sms.common.entity.general.Permission;
import org.test.sms.common.entity.general.UserGroup;
import org.test.sms.common.filter.general.AbstractFilter;
import org.test.sms.common.filter.general.UserGroupFilter;
import org.test.sms.common.utils.Utils;
import org.test.sms.server.dao.interfaces.general.UserGroupDao;

import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
public class UserGroupDaoImpl extends AbstractDaoImpl<UserGroup> implements UserGroupDao {

    @Override
    protected void initSubEntities(UserGroup entity, LocalDateTime now, boolean isAdd) {
        entity.getPermissions().forEach(permission -> {
            if (isAdd) {
                permission.setCreationTime(now);
            }
            permission.setLastModifiedTime(now);
        });
    }

    @Override
    protected UserGroup init(UserGroup entity) {
        List<Permission> permissions = entity.getPermissions();
        permissions.size();
        permissions.forEach(permission -> permission.getPermissionTypes().size());

        return entity;
    }

    @Override
    protected String getSelect() {
        return "id, name";
    }

    @Override
    protected void addFilter(StringBuilder queryBuilder, Map<String, Object> params, AbstractFilter abstractFilter) {
        UserGroupFilter filter = (UserGroupFilter) abstractFilter;

        Long id = filter.getId();
        if (Objects.nonNull(id)) {
            queryBuilder.append(" AND id = :id");
            params.put("id", id);
        }

        List<String> names = filter.getNames();
        if (!Utils.isBlank(names)) {
            queryBuilder.append(" AND name IN(:names)");
            params.put("names", names);
        }
    }

    @Override
    protected String getOrderBy() {
        return "name";
    }

//    misc

    @Override
    public boolean exists(String name) {
        TypedQuery<UserGroup> query = em.createQuery("SELECT new UserGroup(id) FROM UserGroup WHERE UPPER(name) = :name", UserGroup.class);
        query.setParameter("name", name.toUpperCase());

        return !Utils.isBlank(query.getResultList());
    }

    @Override
    public List<UserGroup> getListForSelection() {
        return em.createQuery("SELECT new UserGroup(id, name) FROM UserGroup ORDER BY name", UserGroup.class).getResultList();
    }
}