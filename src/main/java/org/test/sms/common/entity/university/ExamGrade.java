package org.test.sms.common.entity.university;

import lombok.Getter;
import lombok.Setter;
import org.test.sms.common.enums.university.ExamType;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;

@Embeddable
@Getter @Setter
public class ExamGrade implements Serializable {

    @Enumerated(EnumType.STRING)
    @Column(name = "EXAM_TYPE")
    private ExamType examType;

    @Column(name = "GRADE")
    private int grade;
}