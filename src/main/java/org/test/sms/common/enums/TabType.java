package org.test.sms.common.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum TabType {

    BUILDING(TabPermissionType.ADD, TabPermissionType.DELETE, TabPermissionType.EDIT, TabPermissionType.VIEW),

    COURSE(TabPermissionType.ADD, TabPermissionType.DELETE, TabPermissionType.EDIT, TabPermissionType.VIEW),

    EXAM(TabPermissionType.ADD, TabPermissionType.DELETE, TabPermissionType.EDIT, TabPermissionType.VIEW),

    FACULTY(TabPermissionType.ADD, TabPermissionType.DELETE, TabPermissionType.EDIT, TabPermissionType.VIEW),

    LECTURER(TabPermissionType.ADD, TabPermissionType.DELETE, TabPermissionType.EDIT, TabPermissionType.VIEW),

    PROFILE(TabPermissionType.LECTURER_PROFILE, TabPermissionType.STUDENT_PROFILE),

    STUDENT(TabPermissionType.ADD, TabPermissionType.DELETE, TabPermissionType.EDIT, TabPermissionType.VIEW),

    TEXT(TabPermissionType.ADD, TabPermissionType.DELETE, TabPermissionType.EDIT, TabPermissionType.VIEW),

    USER(TabPermissionType.ADD, TabPermissionType.DELETE, TabPermissionType.EDIT, TabPermissionType.VIEW),

    USER_GROUP(TabPermissionType.ADD, TabPermissionType.DELETE, TabPermissionType.EDIT, TabPermissionType.VIEW);

    private TabPermissionType[] permissions;

    TabType(TabPermissionType... permissions) {
        this.permissions = permissions;
    }

    public List<TabPermissionType> getPermissions() {
        return Arrays.stream(permissions).collect(Collectors.toList());
    }
}