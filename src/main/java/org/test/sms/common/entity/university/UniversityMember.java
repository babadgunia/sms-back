package org.test.sms.common.entity.university;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.test.sms.common.entity.general.AbstractEntity;
import org.test.sms.common.enums.general.GenderType;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;
import javax.persistence.MappedSuperclass;
import java.time.LocalDate;

@MappedSuperclass
@NoArgsConstructor
@Getter @Setter
public abstract class UniversityMember extends AbstractEntity {

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(name = "GENDER")
    private GenderType gender;

    @Column(name = "BIRTH_DATE")
    private LocalDate birthDate;

    @Column(name = "PERSONAL_NUMBER")
    private String personalNumber;

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "UNI_EMAIL")
    private String uniEmail;

    @Lob
    @Column(name = "PHOTO")
    private byte[] photo;

    public UniversityMember(long id) {
        super(id);
    }
}