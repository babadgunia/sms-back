package org.test.sms.common.entities.general;

import org.test.sms.common.entities.AppEntity;
import org.test.sms.common.enums.TabPermissionType;
import org.test.sms.common.enums.TabType;

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
@SequenceGenerator(name = Tab.SEQUENCE_NAME, sequenceName = Tab.SEQUENCE_NAME, allocationSize = AppEntity.SEQUENCE_ALLOCATION_SIZE)
public class Tab extends AppEntity {

    static final String SEQUENCE_NAME = SEQUENCE_PREFIX + "TAB" + SEQUENCE_SUFFIX;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_NAME)
    private long id;

    @Enumerated(EnumType.STRING)
    private TabType type;

    private int position;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<TabPermissionType> permissions = new ArrayList<>();

    @ManyToOne
    private UserGroup userGroup;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public TabType getType() {
        return type;
    }

    public void setType(TabType type) {
        this.type = type;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public List<TabPermissionType> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<TabPermissionType> permissions) {
        this.permissions = permissions;
    }

    public UserGroup getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(UserGroup userGroup) {
        this.userGroup = userGroup;
    }
}