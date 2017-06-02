package org.test.sms.common.entity.university;

import org.test.sms.common.enums.university.CourseModuleType;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;

@Embeddable
public class ModuleGrade implements Serializable {

    @Enumerated(EnumType.STRING)
    private CourseModuleType type;

    private int grade;

    public CourseModuleType getType() {
        return type;
    }

    public void setType(CourseModuleType type) {
        this.type = type;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }
}