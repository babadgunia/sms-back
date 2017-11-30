package org.test.sms.common.entity.university;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
@NoArgsConstructor
@Getter @Setter
public class StudentCourse extends AbstractEntity {

    static final String SEQUENCE_NAME = "STUDENT_COURSE" + SEQUENCE_SUFFIX;

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

    public StudentCourse(long id) {
        super(id);
    }
}