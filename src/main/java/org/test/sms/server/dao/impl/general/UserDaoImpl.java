package org.test.sms.server.dao.impl.general;

import org.springframework.stereotype.Repository;
import org.test.sms.common.entity.general.Permission;
import org.test.sms.common.entity.general.User;
import org.test.sms.common.enums.general.LanguageType;
import org.test.sms.common.enums.general.StatusType;
import org.test.sms.common.filter.general.AbstractFilter;
import org.test.sms.common.filter.general.UserFilter;
import org.test.sms.common.utils.Utils;
import org.test.sms.server.dao.interfaces.general.UserDao;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Repository
public class UserDaoImpl extends AbstractDaoImpl<User> implements UserDao {

    @Override
    protected User init(User entity) {
        List<Permission> permissions = entity.getUserGroup().getPermissions();
        permissions.size();
        permissions.forEach(permission -> permission.getPermissionTypes().size());

        return entity;
    }

    @Override
    protected String getSelect() {
        return "id, username, email, name, status, language, userGroup.name";
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

        String email = filter.getEmail();
        if (!Utils.isBlank(email)) {
            queryBuilder.append(" AND UPPER(email) LIKE :email");
            params.put("email", "%" + email.toUpperCase() + "%");
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

        Long userGroupId = filter.getUserGroupId();
        if (!Objects.isNull(userGroupId)) {
            queryBuilder.append(" AND userGroup.id = :userGroupId");
            params.put("userGroupId", userGroupId);
        }
    }

    @Override
    protected String getOrderBy() {
        return "username";
    }

    @Override
    public boolean exists(String username) {
        TypedQuery<User> query = em.createQuery("SELECT new User(id) FROM User WHERE UPPER(username) = :username", User.class);
        query.setParameter("username", username.toUpperCase());

        return !Utils.isBlank(query.getResultList());
    }

    @Override
    public boolean exists(String username, long id) {
        TypedQuery<User> query = em.createQuery("SELECT new User(id) FROM User WHERE UPPER(username) = :username AND id != :id", User.class);
        query.setParameter("username", username.toUpperCase());
        query.setParameter("id", id);

        return !Utils.isBlank(query.getResultList());
    }

    @Override
    public boolean exists(long userGroupId) {
        TypedQuery<User> query = em.createQuery("SELECT new User(id) FROM User WHERE userGroup.id = :userGroupId", User.class);
        query.setParameter("userGroupId", userGroupId);

        return !Utils.isBlank(query.getResultList());
    }

//    misc

    @Override
    public Optional<User> getForAuthByUsername(String username) {
        TypedQuery<User> query = em.createQuery("SELECT new User(password, status, userGroup) FROM User WHERE UPPER(username) = :username", User.class);
        query.setParameter("username", username.toUpperCase());

        try {
            return Optional.of(init(query.getSingleResult()));
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> getForPermissionCheckByUsername(String username) {
        TypedQuery<User> query = em.createQuery("SELECT new User(userGroup) FROM User WHERE UPPER(username) = :username", User.class);
        query.setParameter("username", username.toUpperCase());

        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public Optional<User> getForPasswordResetByEmailOrUsername(String usernameOrEmail) {
        TypedQuery<User> query = em.createQuery("SELECT new User(id, username, email) FROM User WHERE UPPER(email) = :email OR UPPER(username) = :username", User.class);

        usernameOrEmail = usernameOrEmail.toUpperCase();
        query.setParameter("email", usernameOrEmail);
        query.setParameter("username", usernameOrEmail);

        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public void saveNewPassword(long userId, String password) {
        Query query = em.createQuery("UPDATE User u SET u.password = :password WHERE u.id = :id")
                .setParameter("password", password)
                .setParameter("id", userId);

        query.executeUpdate();
    }
}