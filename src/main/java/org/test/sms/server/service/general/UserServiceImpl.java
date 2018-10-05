package org.test.sms.server.service.general;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.test.sms.common.entity.general.User;
import org.test.sms.common.entity.general.UserGroup;
import org.test.sms.common.enums.general.ErrorCodeType;
import org.test.sms.common.enums.general.PermissionGroupType;
import org.test.sms.common.enums.general.PermissionType;
import org.test.sms.common.exception.AppException;
import org.test.sms.common.filter.general.AbstractFilter;
import org.test.sms.common.service.general.MailService;
import org.test.sms.common.service.general.UserService;
import org.test.sms.common.utils.AppUtils;
import org.test.sms.common.utils.Utils;
import org.test.sms.server.dao.interfaces.general.PermissionDao;
import org.test.sms.server.dao.interfaces.general.UserDao;

import java.util.List;
import java.util.Optional;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

    private UserDao dao;

    private PermissionDao permissionDao;

    private MailService mailService;

    @Autowired
    public UserServiceImpl(UserDao dao, PermissionDao permissionDao, MailService mailService) {
        this.dao = dao;
        this.permissionDao = permissionDao;
        this.mailService = mailService;
    }

    @Override
    public User add(User entity) throws AppException {
        String username = entity.getUsername();
        if (dao.exists(username)) {
            throw new AppException(ErrorCodeType.USERNAME_EXISTS, username);
        }

        String password = Utils.generateRandomPassword();
        entity.setPassword(new BCryptPasswordEncoder().encode(password));

        User user = dao.add(entity);

        mailService.sendCredentialsMail(entity, password);

        return user;
    }

    @Override
    public User update(User entity) throws AppException {
        long id = entity.getId();
        Optional<User> storedEntityWrapper = dao.get(id);

        if (storedEntityWrapper.isPresent()) {
            User storedEntity = storedEntityWrapper.get();

            String username = storedEntity.getUsername();
            if (dao.exists(username, id)) {
                throw new AppException(ErrorCodeType.USERNAME_EXISTS, username);
            }

            AppUtils.setEntityVersion(storedEntity, entity.getVersion());
            storedEntity.setEmail(entity.getEmail());
            storedEntity.setName(entity.getName());
            storedEntity.setStatus(entity.getStatus());
            storedEntity.setLanguage(entity.getLanguage());
            storedEntity.setUserGroup(entity.getUserGroup());

            return dao.update(storedEntity);
        }

        throw new AppException(ErrorCodeType.UNKNOWN_ERROR);
    }

    @Override
    public void delete(long id) throws AppException {
//        TODO maybe some checks needed?
        dao.delete(id);
    }

    @Override
    public Optional<User> get(long id) {
        Optional<User> result = dao.get(id);

        result.ifPresent(user -> {
            UserGroup userGroup = user.getUserGroup();
            user.setUserGroup(new UserGroup(userGroup.getId(), userGroup.getName()));
        });

        return result;
    }

    @Override
    public long getCount(AbstractFilter filter) {
//        TODO implement name filter
        return dao.getCount(filter);
    }

    @Override
    public List<User> getList(AbstractFilter filter) {
//        TODO implement name filter
        return dao.getList(filter);
    }

//    misc

    @Override
    public boolean hasPermission(PermissionGroupType permissionGroup, PermissionType permissionType) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<User> userWrapper = dao.getForPermissionCheckByUsername(username);
        if (userWrapper.isPresent()) {
            return permissionDao.exists(userWrapper.get().getUserGroup().getId(), permissionGroup, permissionType);
        }

        return false;
    }
}