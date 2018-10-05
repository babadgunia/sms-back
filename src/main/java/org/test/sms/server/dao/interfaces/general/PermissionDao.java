package org.test.sms.server.dao.interfaces.general;

import org.test.sms.common.entity.general.Permission;
import org.test.sms.common.enums.general.PermissionGroupType;
import org.test.sms.common.enums.general.PermissionType;

public interface PermissionDao extends AbstractDao<Permission> {

    boolean exists(long userGroupId, PermissionGroupType permissionGroup, PermissionType permissionType);
}