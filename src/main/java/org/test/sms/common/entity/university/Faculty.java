package org.test.sms.common.entity.university;

import org.test.sms.common.entity.AppEntity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import java.util.ArrayList;
import java.util.List;

@Entity
@SequenceGenerator(name = Faculty.SEQUENCE_NAME, sequenceName = Faculty.SEQUENCE_NAME, allocationSize = AppEntity.SEQUENCE_ALLOCATION_SIZE)
public class Faculty extends AppEntity {

    static final String SEQUENCE_NAME = SEQUENCE_PREFIX + "FACULTY" + SEQUENCE_SUFFIX;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_NAME)
    private long id;

    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "faculty", cascade = CascadeType.MERGE)
    private List<Course> courses = new ArrayList<>();

    public Faculty() {
    }

    public Faculty(long id) {
        this.id = id;
    }

    public Faculty(long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}