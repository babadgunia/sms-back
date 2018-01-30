package org.test.sms.common.entity.university;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.test.sms.common.entity.general.AbstractEntity;
import org.test.sms.common.enums.general.GenderType;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;
import javax.persistence.MappedSuperclass;
import java.time.LocalDate;

@MappedSuperclass
@NoArgsConstructor
@Getter @Setter
public abstract class UniversityMember extends AbstractEntity {

    private String firstName;

    private String lastName;

    @Enumerated(EnumType.STRING)
    private GenderType gender;

    private LocalDate birthDate;

    private String personalNumber;

    private String phoneNumber;

    private String address;

    private String email;

    private String uniEmail;

    @Lob
    private byte[] photo;

    public UniversityMember(long id) {
        super(id);
    }

    public UniversityMember(String firstName, String lastName, String personalNumber, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.personalNumber = personalNumber;
        this.phoneNumber = phoneNumber;
    }
}