package org.test.sms.common.entity.university;

import org.test.sms.common.enums.university.ExamType;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;

@Embeddable
public class ExamGrade implements Serializable {

    @Enumerated(EnumType.STRING)
    private ExamType type;

    private int grade;

    public ExamType getType() {
        return type;
    }

    public void setType(ExamType type) {
        this.type = type;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }
}