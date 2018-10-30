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
import org.test.sms.common.filter.general.UserFilter;
import org.test.sms.common.service.general.MailService;
import org.test.sms.common.service.general.UserService;
import org.test.sms.common.utils.AppUtils;
import org.test.sms.common.utils.Utils;
import org.test.sms.server.dao.interfaces.general.PermissionDao;
import org.test.sms.server.dao.interfaces.general.UserDao;

import java.util.Optional;

@Service("userService")
@Transactional
public class UserServiceImpl extends AbstractServiceImpl<User> implements UserService {

    private PermissionDao permissionDao;

    private MailService mailService;

    @Autowired
    public UserServiceImpl(UserDao dao, PermissionDao permissionDao, MailService mailService) {
        super(dao);

        this.permissionDao = permissionDao;
        this.mailService = mailService;
    }

    @Override
    protected void processEntity(User entity) {
        String password = Utils.generateRandomPassword();
        entity.setPassword(new BCryptPasswordEncoder().encode(password));
    }

    @Override
    protected void validateSave(User entity) throws AppException {
        String username = entity.getUsername();
        if (((UserDao) dao).exists(username)) {
            throw new AppException(ErrorCodeType.USERNAME_EXISTS, username);
        }
    }

    @Override
    protected void performAdditionalOperationsPostAdd(User entity) {
        mailService.sendCredentialsMail(entity, entity.getPassword());
    }

    @Override
    public User update(User entity) throws AppException {
        long id = entity.getId();

        UserFilter userFilter = new UserFilter();
        userFilter.setId(id);

        Optional<User> storedEntityWrapper = dao.get(userFilter);

        if (storedEntityWrapper.isPresent()) {
            User storedEntity = storedEntityWrapper.get();

            String username = storedEntity.getUsername();
            if (((UserDao) dao).exists(username, id)) {
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
    public Optional<User> get(AbstractFilter abstractFilter) {
        Optional<User> result = dao.get(abstractFilter);

        result.ifPresent(user -> {
            UserGroup userGroup = user.getUserGroup();
            user.setUserGroup(new UserGroup(userGroup.getId(), userGroup.getName()));
        });

        return result;
    }

//    misc

    @Override
    public boolean hasPermission(PermissionGroupType permissionGroup, PermissionType permissionType) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<User> userWrapper = ((UserDao) dao).getForPermissionCheckByUsername(username);
        if (userWrapper.isPresent()) {
            return permissionDao.exists(userWrapper.get().getUserGroup().getId(), permissionGroup, permissionType);
        }

        return false;
    }
}