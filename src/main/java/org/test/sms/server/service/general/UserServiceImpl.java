package org.test.sms.server.service.general;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.test.sms.common.entity.general.User;
import org.test.sms.common.enums.general.ErrorCode;
import org.test.sms.common.enums.general.PermissionGroupType;
import org.test.sms.common.enums.general.PermissionType;
import org.test.sms.common.exception.AppException;
import org.test.sms.common.filter.AbstractFilter;
import org.test.sms.common.service.general.UserService;
import org.test.sms.common.utils.Utils;
import org.test.sms.server.dao.interfaces.general.UserDao;
import org.test.sms.server.service.MailService;

import java.util.List;
import java.util.Optional;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

    private UserDao dao;

    private MailService mailService;

    @Autowired
    public UserServiceImpl(UserDao dao, MailService mailService) {
        this.dao = dao;
        this.mailService = mailService;
    }

    @Override
    public User add(User entity) throws AppException {
        String username = entity.getUsername();
        if (dao.exists(username)) {
            throw new AppException(ErrorCode.USERNAME_EXISTS, username);
        }

        String password = Utils.generateRandomPassword();
        entity.setPassword(new BCryptPasswordEncoder().encode(password));

        User user = dao.add(entity);

        mailService.sendCredentialsMail(entity, password);

        return user;
    }

    @Override
    public User update(User entity) throws AppException {
        return dao.update(entity);
    }

    @Override
    public void delete(long id) throws AppException {
        dao.delete(id);
    }

    @Override
    public Optional<User> get(long id) {
        return dao.get(id);
    }

    @Override
    public long getCount(AbstractFilter filter) {
        return dao.getCount(filter);
    }

    @Override
    public List<User> getList(AbstractFilter filter) {
        return dao.getList(filter);
    }

    @Override
    public Optional<User> get(String username) {
        return dao.get(username);
    }

    @Override
    public boolean exists(long userGroupId) {
        return dao.exists(userGroupId);
    }

    @Override
    public boolean hasPermission(PermissionGroupType permissionGroup, PermissionType permissionType) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        return dao.hasPermission(username, permissionGroup, permissionType);
    }

    @Override
    public void resetPassword(long id) {
        mailService.sendPasswordResetMail(id);
    }
}