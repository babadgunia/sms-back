package org.test.sms.common.entity.general;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.ZonedDateTime;

@Entity
@Table(name = PasswordResetToken.TABLE_NAME)
@NoArgsConstructor
@Getter @Setter
public class PasswordResetToken extends AbstractEntity {

    static final String TABLE_NAME = "PASSWORD_RESET_TOKEN";

    private static final String SEQUENCE_NAME = SEQUENCE_PREFIX + TABLE_NAME;

    @Id
    @SequenceGenerator(name = SEQUENCE_NAME, sequenceName = SEQUENCE_NAME, allocationSize = SEQUENCE_ALLOCATION_SIZE)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_NAME)
    @Column(name = "ID")
    private long id;

    @Column(name = "TOKEN")
    private String token;

    @Column(name = "EXPIRY_DATE")
    private ZonedDateTime expiryDate;

    @OneToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    public PasswordResetToken(long id) {
        super(id);
    }
}