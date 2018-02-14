package org.test.sms.server.dao.interfaces.general;

import org.test.sms.common.entity.general.User;

import java.util.Optional;

public interface UserDao extends AbstractDao<User> {

    boolean exists(String username);

    boolean exists(String username, long id);

    boolean exists(long userGroupId);

    Optional<User> getForAuthByUsername(String username);

    Optional<User> getForPermissionCheckByUsername(String username);

    Optional<User> getForPasswordResetByEmailOrUsername(String usernameOrEmail);

    void saveNewPassword(long userId, String password);
}