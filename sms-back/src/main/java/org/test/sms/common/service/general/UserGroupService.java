package org.test.sms.common.service.general;

import org.test.sms.common.entity.general.UserGroup;

import java.util.List;

public interface UserGroupService extends AbstractService<UserGroup> {

    List<UserGroup> getListForSelection();
}