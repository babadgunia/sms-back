package org.test.sms.common.entity.general;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
@SequenceGenerator(name = Permission.SEQUENCE_NAME, sequenceName = Permission.SEQUENCE_NAME, allocationSize = AbstractEntity.SEQUENCE_ALLOCATION_SIZE)
@NoArgsConstructor
@Getter @Setter
public class Permission extends AbstractEntity {

    static final String SEQUENCE_NAME = "PERMISSION" + SEQUENCE_SUFFIX;

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
    private List<PermissionType> permissionTypes = new ArrayList<>();

    public Permission(long id) {
        super(id);
    }
}