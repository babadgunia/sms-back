package org.test.sms.server.dao.impl.general;

import org.springframework.stereotype.Repository;
import org.test.sms.common.entity.general.Permission;
import org.test.sms.common.entity.general.User;
import org.test.sms.common.entity.general.UserGroup;
import org.test.sms.common.enums.general.ErrorCode;
import org.test.sms.common.enums.general.PermissionGroupType;
import org.test.sms.common.enums.general.PermissionType;
import org.test.sms.common.exception.AppException;
import org.test.sms.common.filter.AbstractFilter;
import org.test.sms.common.filter.general.UserFilter;
import org.test.sms.common.utils.Utils;
import org.test.sms.server.dao.impl.AbstractDaoImpl;
import org.test.sms.server.dao.interfaces.general.UserDao;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Repository
public class UserDaoImpl extends AbstractDaoImpl<User> implements UserDao {

    @Override
    public User add(User entity) throws AppException {
        String username = entity.getUsername();
        if (exists(username)) {
            throw new AppException(ErrorCode.USERNAME_EXISTS, username);
        }

        return super.add(entity);
    }

    private boolean exists(String username) {
        TypedQuery<User> query = em.createQuery("SELECT new User(id) FROM User WHERE UPPER(username) = :username", User.class);
        query.setParameter("username", username.toUpperCase());

        return !Utils.isBlank(query.getResultList());
    }

    @Override
    public Optional<User> get(long id) {
        Optional<User> result = super.get(id);

        result.ifPresent(e -> {
            List<Permission> permissions = e.getUserGroup().getPermissions();
            permissions.size();
            permissions.forEach(f -> f.getPermissions().size());
        });

        return result;
    }

    @Override
    public boolean exists(long userGroupId) {
        TypedQuery<User> query = em.createQuery("SELECT new User(id) FROM User WHERE userGroup.id = :userGroupId", User.class);
        query.setParameter("userGroupId", userGroupId);

        return !Utils.isBlank(query.getResultList());
    }

    @Override
    public Optional<User> get(String username) {
        TypedQuery<User> query = em.createQuery
                ("SELECT new User(id, username, password, name, status, language, userGroup) FROM User WHERE UPPER(username) = :username", User.class);
        query.setParameter("username", username.toUpperCase());

        try {
            User user = query.getSingleResult();

            List<Permission> permissions = user.getUserGroup().getPermissions();
            permissions.size();
            permissions.forEach(e -> e.getPermissions().size());

            return Optional.of(user);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public boolean hasPermission(String username, PermissionGroupType permissionGroup, PermissionType permissionType) {
        User user = em.createQuery("SELECT new User(id, userGroup) FROM User WHERE UPPER(username) = :username", User.class)
                .setParameter("username", username.toUpperCase())
                .getSingleResult();

        if(user != null) {
            Permission permission = em.createQuery(
                    "FROM Permission u WHERE u.permissionGroup = :permissionGroup AND u.userGroup = :userGroup AND EXISTS (SELECT x FROM u.permissions x WHERE x = :permissionType)", Permission.class)
                    .setParameter("userGroup", user.getUserGroup())
                    .setParameter("permissionGroup", permissionGroup)
                    .setParameter("permissionType", permissionType)
                    .getSingleResult();
            return permission != null;
        }
        return false;
    }

    @Override
    protected String getSelect() {
        return "id, name, username, userGroup.id, userGroup.name";
    }

    @Override
    protected void addFilter(StringBuilder queryBuilder, Map<String, Object> params, AbstractFilter abstractFilter) {
        UserFilter filter = (UserFilter) abstractFilter;

        Long id = filter.getId();
        if (Objects.nonNull(id)) {
            queryBuilder.append(" AND id = :id");
            params.put("id", id);
        }

        String username = filter.getUsername();
        if (Objects.nonNull(username)) {
            queryBuilder.append(" AND UPPER(username) LIKE :username");
            params.put("username", "%" + username.toUpperCase() + "%");
        }

        List<String> names = filter.getNames();
        if (Objects.nonNull(names)) {
            queryBuilder.append(" AND name IN(:names)");
            params.put("names", names);
        }

        UserGroup userGroup = filter.getUserGroup();
        if (Objects.nonNull(userGroup)) {
            queryBuilder.append(" AND userGroup = :userGroup");
            params.put("userGroup", userGroup);
        }
    }

    @Override
    protected String getOrderBy() {
        return "username";
    }


}