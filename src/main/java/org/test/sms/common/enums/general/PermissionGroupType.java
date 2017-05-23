package org.test.sms.common.enums.general;

import java.util.Arrays;
import java.util.List;

public enum PermissionGroupType {

    BUILDING(PermissionType.ADD, PermissionType.DELETE, PermissionType.EDIT, PermissionType.VIEW),

    COURSE(PermissionType.ADD, PermissionType.DELETE, PermissionType.EDIT, PermissionType.VIEW),

    EXAM(PermissionType.ADD, PermissionType.DELETE, PermissionType.EDIT, PermissionType.VIEW),

    FACULTY(PermissionType.ADD, PermissionType.DELETE, PermissionType.EDIT, PermissionType.VIEW),

    LECTURER(PermissionType.ADD, PermissionType.DELETE, PermissionType.EDIT, PermissionType.VIEW),

    STUDENT(PermissionType.ADD, PermissionType.DELETE, PermissionType.EDIT, PermissionType.VIEW),

    TEXT(PermissionType.ADD, PermissionType.DELETE, PermissionType.EDIT, PermissionType.VIEW),

    USER(PermissionType.ADD, PermissionType.DELETE, PermissionType.EDIT, PermissionType.VIEW),

    USER_GROUP(PermissionType.ADD, PermissionType.DELETE, PermissionType.EDIT, PermissionType.VIEW);

    private List<PermissionType> permissions;

    PermissionGroupType(PermissionType... permissions) {
        this.permissions = Arrays.asList(permissions);
    }

    public List<PermissionType> getPermissions() {
        return permissions;
    }
}