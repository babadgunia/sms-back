package org.test.sms.common.entity.university;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.test.sms.common.entity.general.AbstractEntity;
import org.test.sms.common.entity.general.User;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import java.util.ArrayList;
import java.util.List;

@Entity
@SequenceGenerator(name = Lecturer.SEQUENCE_NAME, sequenceName = Lecturer.SEQUENCE_NAME, allocationSize = AbstractEntity.SEQUENCE_ALLOCATION_SIZE)
@NoArgsConstructor
@Getter @Setter
public class Lecturer extends UniversityMember {

    static final String SEQUENCE_NAME = "LECTURER" + AbstractEntity.SEQUENCE_SUFFIX;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_NAME)
    private long id;

    @OneToOne(cascade = CascadeType.REMOVE)
    private User user;

    @OneToMany(mappedBy = "lecturer")
    private List<Group> groups = new ArrayList<>();

    public Lecturer(long id) {
        super(id);
    }

    public Lecturer(long id, String firstName, String lastName, String personalNumber, String phoneNumber, User user) {
        super(firstName, lastName, personalNumber, phoneNumber);

        this.id = id;
        this.user = user;
    }
}