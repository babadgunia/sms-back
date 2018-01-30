package org.test.sms.common.entity.university;

import lombok.Getter;
import lombok.Setter;
import org.test.sms.common.enums.university.CourseModuleType;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;

@Embeddable
@Getter @Setter
public class ModuleGrade implements Serializable {

    @Enumerated(EnumType.STRING)
    private CourseModuleType type;

    private int grade;
}