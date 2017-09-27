package org.test.sms.server.dao.interfaces.general;

import org.test.sms.common.entity.general.UserGroup;

import java.util.List;

public interface UserGroupDao extends AbstractDao<UserGroup> {

    List<UserGroup> getListForSelection();

    boolean exists(String name);
}