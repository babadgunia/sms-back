package org.test.sms.server.service.general;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.test.sms.common.enums.general.PermissionGroupType;
import org.test.sms.common.enums.general.PermissionType;
import org.test.sms.common.utils.Utils;
import org.test.sms.server.dao.interfaces.general.UserDao;

@Component("permissionsService")
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class UserPermissionService {

    @Autowired
    private UserDao userDao;

    public boolean hasPermission(String permission, String permissionType) {
        if (Utils.isBlank(permission)) return false;
        return userDao.hasPermission(SecurityContextHolder.getContext().getAuthentication().getName(), PermissionGroupType.valueOf(permission), PermissionType.valueOf(permissionType));
    }

}
