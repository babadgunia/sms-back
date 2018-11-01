package org.test.sms.common.entity.university;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.test.sms.common.entity.general.AbstractEntity;
import org.test.sms.common.enums.university.ExamType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.ZonedDateTime;

@Entity
@Table(name = Exam.TABLE_NAME)
@NoArgsConstructor
@Getter @Setter
public class Exam extends AbstractEntity {

    static final String TABLE_NAME = "EXAM";

    private static final String SEQUENCE_NAME = SEQUENCE_PREFIX + TABLE_NAME;

    @Id
    @SequenceGenerator(name = SEQUENCE_NAME, sequenceName = SEQUENCE_NAME, allocationSize = SEQUENCE_ALLOCATION_SIZE)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_NAME)
    @Column(name = "ID")
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE")
    private ExamType type;

    @Column(name = "MAX_GRADE")
    private Integer maxGrade;

    @Column(name = "START_DATE")
    private ZonedDateTime startDate;

    @Column(name = "END_DATE")
    private ZonedDateTime endDate;

    @Column(name = "NUM_STUDENTS")
    private Integer numStudents;

    @ManyToOne
    @JoinColumn(name = "COURSE_ID")
    private Course course;

    @ManyToOne
    @JoinColumn(name = "AUDITORIUM_ID")
    private Auditorium auditorium;

    public Exam(long id) {
        super(id);
    }
}