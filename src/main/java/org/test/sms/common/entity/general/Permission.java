package org.test.sms.common.entity.general;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.test.sms.common.enums.general.PermissionGroupType;
import org.test.sms.common.enums.general.PermissionType;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = Permission.TABLE_NAME)
@NoArgsConstructor
@Getter @Setter
public class Permission extends AbstractEntity {

    static final String TABLE_NAME = "PERMISSION";

    private static final String SEQUENCE_NAME = SEQUENCE_PREFIX + TABLE_NAME;

    @Id
    @SequenceGenerator(name = SEQUENCE_NAME, sequenceName = SEQUENCE_NAME, allocationSize = SEQUENCE_ALLOCATION_SIZE)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_NAME)
    @Column(name = "ID")
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "GROUP_TYPE")
    private PermissionGroupType group;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "PERMISSION_PERMISSION_TYPE", joinColumns = @JoinColumn(name = "PERMISSION_ID"))
    @Column(name = "PERMISSION_TYPE")
    private List<PermissionType> permissionTypes = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "USER_GROUP_ID")
    private UserGroup userGroup;

    public Permission(long id) {
        super(id);
    }
}