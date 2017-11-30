package org.test.sms.common.entity.general;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import java.util.ArrayList;
import java.util.List;

@Entity
@SequenceGenerator(name = UserGroup.SEQUENCE_NAME, sequenceName = UserGroup.SEQUENCE_NAME, allocationSize = AbstractEntity.SEQUENCE_ALLOCATION_SIZE)
@NoArgsConstructor
@Getter @Setter
public class UserGroup extends AbstractEntity {

    static final String SEQUENCE_NAME = "USER_GROUP" + SEQUENCE_SUFFIX;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_NAME)
    private long id;

    private String name;

    @OneToMany(mappedBy = "userGroup", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Permission> permissions = new ArrayList<>();

    public UserGroup(long id) {
        super(id);
    }

    public UserGroup(String name) {
        this.name = name;
    }

    public UserGroup(long id, String name) {
        this(id);

        this.name = name;
    }
}