package org.test.sms.common.entities.university;

import org.test.sms.common.entities.AppEntity;
import org.test.sms.common.entities.User;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import java.util.ArrayList;
import java.util.List;

@Entity
@SequenceGenerator(name = Lecturer.SEQUENCE_NAME, sequenceName = Lecturer.SEQUENCE_NAME, allocationSize = AppEntity.SEQUENCE_ALLOCATION_SIZE)
public class Lecturer extends UniversityMember {

    static final String SEQUENCE_NAME = AppEntity.SEQUENCE_PREFIX + "LECTURER" + AppEntity.SEQUENCE_SUFFIX;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_NAME)
    private long id;

    @JoinColumn(unique = true)
    @OneToOne(cascade = CascadeType.REMOVE)
    private User user;

    @OneToMany(mappedBy = "lecturer")
    private List<Group> groups = new ArrayList<>();

    public Lecturer() {
    }

    public Lecturer(long id) {
        this.id = id;
    }

    public Lecturer(long id, String firstName, String lastName, String personalNumber, String phoneNumber, User user) {
        super(firstName, lastName, personalNumber, phoneNumber);
        this.id = id;
        this.user = user;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}