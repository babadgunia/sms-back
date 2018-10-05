package org.test.sms.server.service.general;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.test.sms.common.entity.general.UserGroup;
import org.test.sms.common.enums.general.ErrorCodeType;
import org.test.sms.common.exception.AppException;
import org.test.sms.common.filter.general.AbstractFilter;
import org.test.sms.common.service.general.UserGroupService;
import org.test.sms.server.dao.interfaces.general.UserDao;
import org.test.sms.server.dao.interfaces.general.UserGroupDao;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserGroupServiceImpl implements UserGroupService {

    private UserGroupDao dao;

    private UserDao userDao;

    @Autowired
    public UserGroupServiceImpl(UserGroupDao dao, UserDao userDao) {
        this.dao = dao;
        this.userDao = userDao;
    }

    @Override
    public UserGroup add(UserGroup entity) throws AppException {
        String name = entity.getName();
        if (dao.exists(name)) {
            throw new AppException(ErrorCodeType.USER_GROUP_EXISTS, name);
        }

        entity.getPermissions().forEach(permission -> permission.setUserGroup(entity));

        return dao.add(entity);
    }

    @Override
    public UserGroup update(UserGroup entity) throws AppException {
        return dao.update(entity);
    }

    @Override
    public void delete(long id) throws AppException {
        if (userDao.exists(id)) {
            throw new AppException(ErrorCodeType.USER_GROUP_HAS_USERS);
        }

        dao.delete(id);
    }

    @Override
    public Optional<UserGroup> get(long id) {
        return dao.get(id);
    }

    @Override
    public long getCount(AbstractFilter filter) {
//        TODO implement name filter
        return dao.getCount(filter);
    }

    @Override
    public List<UserGroup> getList(AbstractFilter filter) {
//        TODO implement name filter
        return dao.getList(filter);
    }

//    misc

    @Override
    public List<UserGroup> getListForSelection() {
        return dao.getListForSelection();
    }
}