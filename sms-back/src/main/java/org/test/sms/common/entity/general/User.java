package org.test.sms.common.entity.general;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.test.sms.common.enums.general.LanguageType;
import org.test.sms.common.enums.general.StatusType;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@SequenceGenerator(name = User.SEQUENCE_NAME, sequenceName = User.SEQUENCE_NAME, allocationSize = AbstractEntity.SEQUENCE_ALLOCATION_SIZE)
@Table(name = "APPLICATION_USER")
@NoArgsConstructor
@Getter @Setter
public class User extends AbstractEntity {

    static final String SEQUENCE_NAME = "APPLICATION_USER" + SEQUENCE_SUFFIX;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_NAME)
    private long id;

    private String username;

    private String password;

    private String email;

    private String name;

    @Enumerated(EnumType.STRING)
    private StatusType status;

    @Enumerated(EnumType.STRING)
    private LanguageType language;

    @ManyToOne
    private UserGroup userGroup;

    public User(long id) {
        super(id);
    }

    public User(long id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }

    public User(UserGroup userGroup) {
        this.userGroup = userGroup;
    }

    public User(String password, StatusType status, UserGroup userGroup) {
        this.password = password;
        this.status = status;
        this.userGroup = userGroup;
    }

    public User(long id, String username, String email, String name, StatusType status, LanguageType language, String userGroupName) {
        this(id);

        this.username = username;
        this.email = email;
        this.name = name;
        this.status = status;
        this.language = language;
        this.userGroup = new UserGroup(userGroupName);
    }
}