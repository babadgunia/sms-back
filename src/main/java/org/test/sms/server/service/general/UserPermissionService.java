package org.test.sms.server.service.general;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.test.sms.common.enums.general.PermissionGroupType;
import org.test.sms.common.enums.general.PermissionType;
import org.test.sms.server.dao.interfaces.general.UserDao;

@Service
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class UserPermissionService {

    private UserDao userDao;

    @Autowired
    public UserPermissionService(UserDao userDao) {
        this.userDao = userDao;
    }

    public boolean hasPermission(PermissionGroupType permissionGroup, PermissionType permission) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        return userDao.hasPermission(username, permissionGroup, permission);
    }
}