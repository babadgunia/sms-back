package org.test.sms.common.filter.university;

import lombok.Getter;
import lombok.Setter;
import org.test.sms.common.entity.university.Building;
import org.test.sms.common.entity.university.Course;
import org.test.sms.common.entity.university.Faculty;
import org.test.sms.common.enums.university.ExamType;
import org.test.sms.common.filter.general.AbstractFilter;

import java.time.LocalDateTime;

@Getter @Setter
public class ExamFilter extends AbstractFilter {

    private Faculty faculty;

    private Course course;

    private LocalDateTime fromDate;

    private LocalDateTime toDate;

    private Building building;

    private ExamType type;
}