package org.test.sms.server.dao.impl.general;

import org.springframework.stereotype.Repository;
import org.test.sms.common.entity.general.Permission;
import org.test.sms.common.enums.general.PermissionGroupType;
import org.test.sms.common.enums.general.PermissionType;
import org.test.sms.common.filter.general.AbstractFilter;
import org.test.sms.common.utils.Utils;
import org.test.sms.server.dao.interfaces.general.PermissionDao;

import javax.persistence.TypedQuery;
import java.util.Map;

@Repository
public class PermissionDaoImpl extends AbstractDaoImpl<Permission> implements PermissionDao {

    @Override
    protected Permission init(Permission entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected String getSelect() {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void addFilter(StringBuilder queryBuilder, Map<String, Object> params, AbstractFilter abstractFilter) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected String getOrderBy() {
        throw new UnsupportedOperationException();
    }

//    misc

    @Override
    public boolean exists(long userGroupId, PermissionGroupType permissionGroup, PermissionType permissionType) {
        TypedQuery<Permission> query = em.createQuery("SELECT new Permission(id) FROM Permission p WHERE userGroup.id = :userGroupId AND permissionGroup = :permissionGroup " +
                "AND EXISTS (SELECT pt FROM p.permissionTypes pt WHERE pt = :permissionType)", Permission.class);
        query.setParameter("userGroupId", userGroupId);
        query.setParameter("permissionGroup", permissionGroup);
        query.setParameter("permissionType", permissionType);

        return !Utils.isBlank(query.getResultList());
    }
}