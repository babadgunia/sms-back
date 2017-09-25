package org.test.sms.server.dao.interfaces.general;

import org.test.sms.common.entity.general.User;
import org.test.sms.server.dao.AbstractDao;

import java.util.Optional;

public interface UserDao extends AbstractDao<User> {

    Optional<User> getForAuth(String username);

    Optional<User> getForPermissionCheck(String username);

    boolean exists(String username);

    boolean exists(long userGroupId);
}