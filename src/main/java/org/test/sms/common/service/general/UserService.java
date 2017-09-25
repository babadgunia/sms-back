package org.test.sms.common.service.general;

import org.test.sms.common.entity.general.User;
import org.test.sms.common.enums.general.PermissionGroupType;
import org.test.sms.common.enums.general.PermissionType;
import org.test.sms.common.service.AbstractService;

import java.util.Optional;

public interface UserService extends AbstractService<User> {

    boolean hasPermission(PermissionGroupType permissionGroup, PermissionType permissionType);

    void sendPasswordResetEmail(String context, String token, User user);

    Optional<User> findUserByUsernameOrEmail(String userEmail);

    void createPasswordResetTokenForUser(User user, String token);
}