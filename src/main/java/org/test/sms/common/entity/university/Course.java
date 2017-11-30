package org.test.sms.common.entity.university;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.test.sms.common.entity.general.AbstractEntity;
import org.test.sms.common.enums.university.SemesterType;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import java.util.ArrayList;
import java.util.List;

@Entity
@SequenceGenerator(name = Course.SEQUENCE_NAME, sequenceName = Course.SEQUENCE_NAME, allocationSize = AbstractEntity.SEQUENCE_ALLOCATION_SIZE)
@NoArgsConstructor
@Getter @Setter
public class Course extends AbstractEntity {

    static final String SEQUENCE_NAME = "COURSE" + SEQUENCE_SUFFIX;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_NAME)
    private long id;

    private String name;

    private Integer numCredits;

    private Integer maxStudents;

    @Lob
    private byte[] syllabus;

    @ManyToOne
    private Faculty faculty;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<SemesterType> semesters = new ArrayList<>();

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Module> modules = new ArrayList<>();

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<Exam> exams = new ArrayList<>();

    @ManyToMany
    private List<Course> prerequisites = new ArrayList<>();

    public Course(long id) {
        super(id);
    }

    public Course(long id, String name) {
        this(id);

        this.name = name;
    }
}