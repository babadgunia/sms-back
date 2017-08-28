package org.test.sms.common.entity.general;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.test.sms.common.entity.AppEntity;
import org.test.sms.common.enums.general.PermissionGroupType;
import org.test.sms.common.enums.general.PermissionType;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import java.util.ArrayList;
import java.util.List;

@Entity
@SequenceGenerator(name = Permission.SEQUENCE_NAME, sequenceName = Permission.SEQUENCE_NAME, allocationSize = AppEntity.SEQUENCE_ALLOCATION_SIZE)
public class Permission extends AppEntity {

    static final String SEQUENCE_NAME = SEQUENCE_PREFIX + "PERMISSION" + SEQUENCE_SUFFIX;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_NAME)
    private long id;

    @Enumerated(EnumType.STRING)
    private PermissionGroupType permissionGroup;

    @ManyToOne
    @JsonIgnore
    private UserGroup userGroup;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<PermissionType> permissions = new ArrayList<>();

    public Permission() {}

    public Permission(long id) {
        super(id);
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public PermissionGroupType getPermissionGroup() {
        return permissionGroup;
    }

    public void setPermissionGroup(PermissionGroupType permissionGroup) {
        this.permissionGroup = permissionGroup;
    }

    public UserGroup getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(UserGroup userGroup) {
        this.userGroup = userGroup;
    }

    public List<PermissionType> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<PermissionType> permissions) {
        this.permissions = permissions;
    }
}