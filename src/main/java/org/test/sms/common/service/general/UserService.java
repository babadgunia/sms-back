package org.test.sms.common.service.general;

import org.test.sms.common.entities.general.User;
import org.test.sms.common.service.AbstractService;

import java.util.Optional;

public interface UserService extends AbstractService<User> {

    Optional<User> get(String username);
}