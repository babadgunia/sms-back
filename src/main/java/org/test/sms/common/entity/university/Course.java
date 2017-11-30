package org.test.sms.common.entity.university;

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

    public Course() {}

    public Course(long id) {
        super(id);
    }

    public Course(long id, String name) {
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

    public Integer getNumCredits() {
        return numCredits;
    }

    public void setNumCredits(Integer numCredits) {
        this.numCredits = numCredits;
    }

    public Integer getMaxStudents() {
        return maxStudents;
    }

    public void setMaxStudents(Integer maxStudents) {
        this.maxStudents = maxStudents;
    }

    public byte[] getSyllabus() {
        return syllabus;
    }

    public void setSyllabus(byte[] syllabus) {
        this.syllabus = syllabus;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public List<SemesterType> getSemesters() {
        return semesters;
    }

    public void setSemesters(List<SemesterType> semesters) {
        this.semesters = semesters;
    }

    public List<Module> getModules() {
        return modules;
    }

    public void setModules(List<Module> modules) {
        this.modules = modules;
    }

    public List<Exam> getExams() {
        return exams;
    }

    public void setExams(List<Exam> exams) {
        this.exams = exams;
    }

    public List<Course> getPrerequisites() {
        return prerequisites;
    }

    public void setPrerequisites(List<Course> prerequisites) {
        this.prerequisites = prerequisites;
    }
}