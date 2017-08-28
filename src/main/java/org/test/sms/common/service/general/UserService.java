package org.test.sms.common.service.general;

import org.test.sms.common.entity.general.User;
import org.test.sms.common.enums.general.PermissionGroupType;
import org.test.sms.common.enums.general.PermissionType;
import org.test.sms.common.service.AbstractService;

import java.util.Optional;

public interface UserService extends AbstractService<User> {

    Optional<User> get(String username);

    boolean exists(String username);

    boolean exists(long userGroupId);

    boolean hasPermission(String username, PermissionGroupType permissionGroup, PermissionType permission);
}