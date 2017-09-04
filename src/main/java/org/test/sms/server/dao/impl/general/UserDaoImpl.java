package org.test.sms.server.dao.impl.general;

import org.springframework.stereotype.Repository;
import org.test.sms.common.entity.general.Permission;
import org.test.sms.common.entity.general.User;
import org.test.sms.common.enums.general.LanguageType;
import org.test.sms.common.enums.general.PermissionGroupType;
import org.test.sms.common.enums.general.PermissionType;
import org.test.sms.common.enums.general.StatusType;
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
    public User update(User entity) throws AppException {
        TypedQuery<String> query = em.createQuery("SELECT password FROM User WHERE id = :id", String.class);
        query.setParameter("id", entity.getId());

        entity.setPassword(query.getSingleResult());

        return super.update(entity);
    }

    @Override
    protected User init(User entity) {
        List<Permission> permissions = entity.getUserGroup().getPermissions();
        permissions.size();
        permissions.forEach(permission -> permission.getPermissions().size());

        return entity;
    }

    @Override
    protected String getSelect() {
        return "id, username, name, status, language";
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
        if (!Utils.isBlank(username)) {
            queryBuilder.append(" AND UPPER(username) LIKE :username");
            params.put("username", "%" + username.toUpperCase() + "%");
        }

        List<String> names = filter.getNames();
        if (!Utils.isBlank(names)) {
            queryBuilder.append(" AND name IN(:names)");
            params.put("names", names);
        }

        StatusType status = filter.getStatus();
        if (!Objects.isNull(status)) {
            queryBuilder.append(" AND status = :status");
            params.put("status", status);
        }

        LanguageType language = filter.getLanguage();
        if (!Objects.isNull(language)) {
            queryBuilder.append(" AND language = :language");
            params.put("language", language);
        }
    }

    @Override
    protected String getOrderBy() {
        return "username";
    }

    @Override
    public Optional<User> get(String username) {
        TypedQuery<User> query = em.createQuery("SELECT new User(username, password, status, userGroup) FROM User WHERE UPPER(username) = :username", User.class);
        query.setParameter("username", username.toUpperCase());

        try {
            return Optional.of(init(query.getSingleResult()));
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public boolean exists(String username) {
        TypedQuery<User> query = em.createQuery("SELECT new User(id) FROM User WHERE UPPER(username) = :username", User.class);
        query.setParameter("username", username.toUpperCase());

        return !Utils.isBlank(query.getResultList());
    }

    @Override
    public boolean exists(long userGroupId) {
        TypedQuery<User> query = em.createQuery("SELECT new User(id) FROM User WHERE userGroup.id = :userGroupId", User.class);
        query.setParameter("userGroupId", userGroupId);

        return !Utils.isBlank(query.getResultList());
    }

    @Override
    public boolean hasPermission(String username, PermissionGroupType permissionGroup, PermissionType permission) {
        try {
            User user = em.createQuery("SELECT new User(userGroup) FROM User WHERE UPPER(username) = :username", User.class)
                    .setParameter("username", username.toUpperCase())
                    .getSingleResult();

            if (Objects.nonNull(user)) {
                em.createQuery(
                        "FROM Permission p WHERE p.permissionGroup = :permissionGroup AND p.userGroup = :userGroup AND EXISTS (SELECT pl FROM p.permissions pl WHERE pl = :permissionType)",
                        Permission.class)
                        .setParameter("userGroup", user.getUserGroup())
                        .setParameter("permissionGroup", permissionGroup)
                        .setParameter("permissionType", permission)
                        .getSingleResult();

                return true;
            }
        } catch (NoResultException e) {
            return false;
        }

        return false;
    }
}