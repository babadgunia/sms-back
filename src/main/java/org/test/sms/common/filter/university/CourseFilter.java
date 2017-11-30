package org.test.sms.common.filter.university;

import lombok.Getter;
import lombok.Setter;
import org.test.sms.common.entity.university.Faculty;
import org.test.sms.common.filter.general.AbstractFilter;

import java.util.List;

@Getter @Setter
public class CourseFilter extends AbstractFilter {

    private Faculty faculty;

    private List<String> names;

    private List<String> faculties;
}