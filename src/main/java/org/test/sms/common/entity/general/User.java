package org.test.sms.common.entity.general;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.cache.annotation.Cacheable;
import org.test.sms.common.enums.general.LanguageType;
import org.test.sms.common.enums.general.StatusType;

import javax.persistence.Column;
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

@Entity
@Table(name = User.TABLE_NAME)
@NoArgsConstructor
@Getter @Setter
@Cacheable("User")
public class User extends AbstractEntity {

    static final String TABLE_NAME = "APP_USER";

    private static final String SEQUENCE_NAME = SEQUENCE_PREFIX + TABLE_NAME;

    @Id
    @SequenceGenerator(name = SEQUENCE_NAME, sequenceName = SEQUENCE_NAME, allocationSize = SEQUENCE_ALLOCATION_SIZE)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_NAME)
    @Column(name = "ID")
    private long id;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "NAME")
    private String name;

    @Column(name = "EMAIL")
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private StatusType status;

    @Enumerated(EnumType.STRING)
    @Column(name = "LANGUAGE")
    private LanguageType language;

    @ManyToOne
    @JoinColumn(name = "USER_GROUP_ID")
    private UserGroup userGroup;

    public User(long id) {
        super(id);
    }
}