package org.test.sms.server.dao.impl.general;

import org.springframework.stereotype.Repository;
import org.test.sms.common.entities.general.Permission;
import org.test.sms.common.entities.general.User;
import org.test.sms.common.entities.general.UserGroup;
import org.test.sms.common.enums.general.ErrorCode;
import org.test.sms.common.exception.AppException;
import org.test.sms.common.filters.AbstractFilter;
import org.test.sms.common.filters.general.UserFilter;
import org.test.sms.common.utils.Utils;
import org.test.sms.server.dao.impl.AbstractDaoImpl;
import org.test.sms.server.dao.interfaces.general.UserDao;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.HashMap;
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
    public List<User> getList(AbstractFilter filter) {
        StringBuilder queryBuilder = new StringBuilder("SELECT new User(id, name, username, userGroup.id, userGroup.name) FROM User WHERE 1 = 1");
        Map<String, Object> params = new HashMap<>();

        addFilter(queryBuilder, params, (UserFilter) filter);

        queryBuilder.append(" ORDER BY username");

        TypedQuery<User> query = em.createQuery(queryBuilder.toString(), User.class);
        params.keySet().forEach(e -> query.setParameter(e, params.get(e)));

        return query.getResultList();
    }

    private void addFilter(StringBuilder queryBuilder, Map<String, Object> params, UserFilter filter) {
        if (Objects.isNull(filter)) {
            return;
        }

        List<String> names = filter.getNames();
        if (Objects.nonNull(names)) {
            queryBuilder.append(" AND name IN(:names)");
            params.put("names", names);
        }

        String username = filter.getUsername();
        if (Objects.nonNull(username)) {
            queryBuilder.append(" AND UPPER(username) LIKE :username");
            params.put("username", "%" + username.toUpperCase() + "%");
        }

        UserGroup userGroup = filter.getUserGroup();
        if (Objects.nonNull(userGroup)) {
            queryBuilder.append(" AND userGroup = :userGroup");
            params.put("userGroup", userGroup);
        }
    }

    @Override
    public boolean exists(long userGroupId) {
        TypedQuery<User> query = em.createQuery("SELECT new User(id) FROM User WHERE userGroup.id = :userGroupId", User.class);
        query.setParameter("userGroupId", userGroupId);

        return !Utils.isBlank(query.getResultList());
    }

    @Override
    public Optional<User> get(String username) {
        TypedQuery<User> query = em.createQuery("SELECT new User(id, username, password, name, userGroup, language) FROM User WHERE UPPER(username) = :username", User.class);
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
}