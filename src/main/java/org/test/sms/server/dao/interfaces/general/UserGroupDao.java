package org.test.sms.server.dao.interfaces.general;

import org.test.sms.common.entity.general.UserGroup;
import org.test.sms.server.dao.AbstractDao;

public interface UserGroupDao extends AbstractDao<UserGroup> {

    boolean exists(String name);
}