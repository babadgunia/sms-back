package org.test.sms.server.service.general;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.test.sms.common.entity.general.UserGroup;
import org.test.sms.common.enums.general.ErrorCodeType;
import org.test.sms.common.exception.AppException;
import org.test.sms.common.service.general.UserGroupService;
import org.test.sms.server.dao.interfaces.general.UserDao;
import org.test.sms.server.dao.interfaces.general.UserGroupDao;

import java.util.List;

@Service
@Transactional
public class UserGroupServiceImpl extends AbstractServiceImpl<UserGroup> implements UserGroupService {

    private UserDao userDao;

    @Autowired
    public UserGroupServiceImpl(UserGroupDao dao, UserDao userDao) {
        super(dao);

        this.userDao = userDao;
    }

    @Override
    public UserGroup add(UserGroup entity) throws AppException {
        String name = entity.getName();
        if (((UserGroupDao) dao).exists(name)) {
            throw new AppException(ErrorCodeType.USER_GROUP_EXISTS, name);
        }

        entity.getPermissions().forEach(permission -> permission.setUserGroup(entity));

        return dao.add(entity);
    }

    @Override
    public void delete(long id) throws AppException {
        if (userDao.exists(id)) {
            throw new AppException(ErrorCodeType.USER_GROUP_HAS_USERS);
        }

        dao.delete(id);
    }

    @Override
    public List<UserGroup> getListForSelection() {
        return ((UserGroupDao) dao).getListForSelection();
    }
}