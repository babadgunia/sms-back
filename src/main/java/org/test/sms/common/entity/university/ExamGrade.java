package org.test.sms.common.entity.university;

import lombok.Getter;
import lombok.Setter;
import org.test.sms.common.enums.university.ExamType;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;

@Embeddable
@Getter @Setter
public class ExamGrade implements Serializable {

    @Enumerated(EnumType.STRING)
    private ExamType type;

    private int grade;
}