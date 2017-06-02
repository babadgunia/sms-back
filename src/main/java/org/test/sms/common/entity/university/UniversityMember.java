package org.test.sms.common.entity.university;

import org.test.sms.common.entity.AppEntity;
import org.test.sms.common.enums.general.GenderType;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;
import javax.persistence.MappedSuperclass;
import java.time.LocalDate;

@MappedSuperclass
public abstract class UniversityMember extends AppEntity {

    private String firstName;

    private String lastName;

    @Enumerated(EnumType.STRING)
    private GenderType gender;

    private LocalDate birthDate;

    @Column(unique = true)
    private String personalNumber;

    private String phoneNumber;

    private String address;

    private String email;

    @Column(unique = true)
    private String uniEmail;

    @Lob
    private byte[] photo;

    public UniversityMember() {
    }

    public UniversityMember(String firstName, String lastName, String personalNumber, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.personalNumber = personalNumber;
        this.phoneNumber = phoneNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public GenderType getGender() {
        return gender;
    }

    public void setGender(GenderType gender) {
        this.gender = gender;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getPersonalNumber() {
        return personalNumber;
    }

    public void setPersonalNumber(String personalNumber) {
        this.personalNumber = personalNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUniEmail() {
        return uniEmail;
    }

    public void setUniEmail(String uniEmail) {
        this.uniEmail = uniEmail;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }
}