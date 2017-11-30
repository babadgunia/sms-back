package org.test.sms.common.entity.university;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.test.sms.common.entity.general.AbstractEntity;
import org.test.sms.common.enums.university.ExamType;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import java.time.LocalDateTime;

@Entity
@SequenceGenerator(name = Exam.SEQUENCE_NAME, sequenceName = Exam.SEQUENCE_NAME, allocationSize = AbstractEntity.SEQUENCE_ALLOCATION_SIZE)
@NoArgsConstructor
@Getter @Setter
public class Exam extends AbstractEntity {

    static final String SEQUENCE_NAME = "EXAM" + SEQUENCE_SUFFIX;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_NAME)
    private long id;

    @Enumerated(EnumType.STRING)
    private ExamType type;

    private Integer maxGrade;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private Integer numStudents;

    @ManyToOne
    private Course course;

    @ManyToOne
    private Building building;

    public Exam(long id) {
        super(id);
    }
}