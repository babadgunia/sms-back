package org.test.sms.common.entity.university;

import lombok.Getter;
import lombok.Setter;
import org.test.sms.common.enums.university.CourseModuleType;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;

@Embeddable
@Getter @Setter
public class ModuleGrade implements Serializable {

    @Enumerated(EnumType.STRING)
    @Column(name = "MODULE_TYPE")
    private CourseModuleType moduleType;

    @Column(name = "GRADE")
    private int grade;
}