package org.test.sms.common.entity.university;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.test.sms.common.entity.general.AbstractEntity;
import org.test.sms.common.enums.university.SemesterType;

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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = StudentCourse.TABLE_NAME)
@NoArgsConstructor
@Getter @Setter
public class StudentCourse extends AbstractEntity {

    static final String TABLE_NAME = "STUDENT_COURSE";

    private static final String SEQUENCE_NAME = SEQUENCE_PREFIX + TABLE_NAME;

    @Id
    @SequenceGenerator(name = SEQUENCE_NAME, sequenceName = SEQUENCE_NAME, allocationSize = SEQUENCE_ALLOCATION_SIZE)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_NAME)
    @Column(name = "ID")
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "SEMESTER")
    private SemesterType semester;

    @Column(name = "YEAR")
    private String year;

    @Column(name = "ATTENDANCE_GRADE")
    private int attendanceGrade;

    @ElementCollection
    @CollectionTable(name = "STUDENT_COURSE_MODULE_GRADE", joinColumns = @JoinColumn(name = "STUDENT_COURSE_ID"))
    private List<ModuleGrade> moduleGrades = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "STUDENT_COURSE_EXAM_GRADE", joinColumns = @JoinColumn(name = "STUDENT_COURSE_ID"))
    private List<ExamGrade> examGrades = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "STUDENT_COURSE_MODULE_GROUP", joinColumns = @JoinColumn(name = "STUDENT_COURSE_ID"), inverseJoinColumns = @JoinColumn(name = "GROUP_ID"))
    @MapKeyJoinColumn(name = "MODULE_ID")
    private Map<Module, Group> moduleGroups = new HashMap<>();

    @ManyToOne
    @JoinColumn(name = "STUDENT_ID")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "COURSE_ID")
    private Course course;

    public StudentCourse(long id) {
        super(id);
    }
}