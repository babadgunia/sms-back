package org.test.sms.server.service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.test.sms.common.enums.general.PermissionGroupType;
import org.test.sms.common.enums.general.PermissionType;
import org.test.sms.common.service.general.UserService;

@Service
@Transactional
public class UserPermissionService {

    private UserService userService;

    @Autowired
    public UserPermissionService(UserService userService) {
        this.userService = userService;
    }

    public boolean hasPermission(PermissionGroupType permissionGroup, PermissionType permission) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        return userService.hasPermission(username, permissionGroup, permission);
    }
}