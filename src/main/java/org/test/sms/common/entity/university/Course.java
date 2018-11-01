package org.test.sms.common.entity.university;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.test.sms.common.entity.general.AbstractEntity;
import org.test.sms.common.enums.university.SemesterType;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = Course.TABLE_NAME)
@NoArgsConstructor
@Getter @Setter
public class Course extends AbstractEntity {

    static final String TABLE_NAME = "COURSE";

    private static final String SEQUENCE_NAME = SEQUENCE_PREFIX + TABLE_NAME;

    @Id
    @SequenceGenerator(name = SEQUENCE_NAME, sequenceName = SEQUENCE_NAME, allocationSize = SEQUENCE_ALLOCATION_SIZE)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_NAME)
    @Column(name = "ID")
    private long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "NUM_CREDITS")
    private Integer numCredits;

    @Column(name = "MAX_STUDENTS")
    private Integer maxStudents;

    @Lob
    @Column(name = "SYLLABUS")
    private byte[] syllabus;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "COURSE_SEMESTER", joinColumns = @JoinColumn(name = "COURSE_ID"))
    @Column(name = "SEMESTER")
    private List<SemesterType> semesters = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "COURSE_PREREQUISITE", joinColumns = @JoinColumn(name = "COURSE_ID"), inverseJoinColumns = @JoinColumn(name = "PREREQUISITE_ID"))
    private List<Course> prerequisites = new ArrayList<>();

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Module> modules = new ArrayList<>();

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Exam> exams = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "FACULTY_ID")
    private Faculty faculty;

    public Course(long id) {
        super(id);
    }
}