package org.test.sms.server.dao.impl.general;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.test.sms.common.entities.general.Permission;
import org.test.sms.common.entities.general.UserGroup;
import org.test.sms.common.enums.general.ErrorCode;
import org.test.sms.common.exception.AppException;
import org.test.sms.common.filters.AbstractFilter;
import org.test.sms.common.utils.Utils;
import org.test.sms.server.dao.impl.AbstractDaoImpl;
import org.test.sms.server.dao.interfaces.general.UserDao;
import org.test.sms.server.dao.interfaces.general.UserGroupDao;

import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class UserGroupDaoImpl extends AbstractDaoImpl<UserGroup> implements UserGroupDao {

    private UserDao userDao;

    @Autowired
    public UserGroupDaoImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserGroup add(UserGroup entity) throws AppException {
        String name = entity.getName();
        if (exists(name)) {
            throw new AppException(ErrorCode.USER_GROUP_EXISTS, name);
        }

        return super.add(entity);
    }

    private boolean exists(String name) {
        TypedQuery<UserGroup> query = em.createQuery("SELECT new UserGroup(id) FROM UserGroup WHERE UPPER(name) = :name", UserGroup.class);
        query.setParameter("name", name.toUpperCase());

        return !Utils.isBlank(query.getResultList());
    }

    @Override
    public void delete(long id) throws AppException {
        if (userDao.exists(id)) {
            throw new AppException(ErrorCode.USER_GROUP_HAS_USERS);
        }

        super.delete(id);
    }

    @Override
    public Optional<UserGroup> get(long id) {
        Optional<UserGroup> result = super.get(id);

        result.ifPresent(e -> {
            List<Permission> permissions = e.getPermissions();
            permissions.size();
            permissions.forEach(f -> f.getPermissions().size());
        });

        return result;
    }

    @Override
    public List<UserGroup> getList(AbstractFilter filter) {
        return em.createQuery("SELECT new UserGroup(id, name) FROM UserGroup ORDER BY name", UserGroup.class).getResultList();
    }
}