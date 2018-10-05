package org.test.sms.common.entity.university;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.test.sms.common.entity.general.AbstractEntity;

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
@SequenceGenerator(name = Faculty.SEQUENCE_NAME, sequenceName = Faculty.SEQUENCE_NAME, allocationSize = AbstractEntity.SEQUENCE_ALLOCATION_SIZE)
@NoArgsConstructor
@Getter @Setter
public class Faculty extends AbstractEntity {

    static final String SEQUENCE_NAME = "FACULTY" + SEQUENCE_SUFFIX;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_NAME)
    private long id;

    private String name;

    @OneToMany(mappedBy = "faculty", cascade = CascadeType.MERGE)
    private List<Course> courses = new ArrayList<>();

    public Faculty(long id) {
        super(id);
    }

    public Faculty(long id, String name) {
        this(id);

        this.name = name;
    }
}