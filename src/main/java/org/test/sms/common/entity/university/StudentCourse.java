package org.test.sms.common.entity.university;

import org.test.sms.common.entity.general.AbstractEntity;
import org.test.sms.common.enums.university.SemesterType;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.SequenceGenerator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@SequenceGenerator(name = StudentCourse.SEQUENCE_NAME, sequenceName = StudentCourse.SEQUENCE_NAME, allocationSize = AbstractEntity.SEQUENCE_ALLOCATION_SIZE)
public class StudentCourse extends AbstractEntity {

    static final String SEQUENCE_NAME = SEQUENCE_PREFIX + "STUDENT_COURSE" + SEQUENCE_SUFFIX;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_NAME)
    private long id;

    @Enumerated(EnumType.STRING)
    private SemesterType semester;

    private String year;

    private int attendanceGrade;

    @ManyToOne
    private Student student;

    @ManyToOne
    private Course course;

    @ElementCollection
    private List<ModuleGrade> moduleGrades = new ArrayList<>();

    @ElementCollection
    private List<ExamGrade> examGrades = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "StudentCourse_Groups", inverseJoinColumns = @JoinColumn(name = "Group_Id"))
    @MapKeyJoinColumn(name = "Module_Id", referencedColumnName = "ID")
    private Map<Module, Group> groups = new HashMap<>();

    public StudentCourse() {}

    public StudentCourse(long id) {
        super(id);
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public SemesterType getSemester() {
        return semester;
    }

    public void setSemester(SemesterType semester) {
        this.semester = semester;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public int getAttendanceGrade() {
        return attendanceGrade;
    }

    public void setAttendanceGrade(int attendanceGrade) {
        this.attendanceGrade = attendanceGrade;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public List<ModuleGrade> getModuleGrades() {
        return moduleGrades;
    }

    public void setModuleGrades(List<ModuleGrade> moduleGrades) {
        this.moduleGrades = moduleGrades;
    }

    public List<ExamGrade> getExamGrades() {
        return examGrades;
    }

    public void setExamGrades(List<ExamGrade> examGrades) {
        this.examGrades = examGrades;
    }

    public Map<Module, Group> getGroups() {
        return groups;
    }

    public void setGroups(Map<Module, Group> groups) {
        this.groups = groups;
    }
}