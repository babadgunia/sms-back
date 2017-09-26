package org.test.sms.common.filter.university;

import org.test.sms.common.entity.university.Faculty;
import org.test.sms.common.filter.general.AbstractFilter;

import java.util.List;

public class CourseFilter extends AbstractFilter {

    private Faculty faculty;

    private List<String> names;

    private List<String> faculties;

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public List<String> getNames() {
        return names;
    }

    public void setNames(List<String> names) {
        this.names = names;
    }

    public List<String> getFaculties() {
        return faculties;
    }

    public void setFaculties(List<String> faculties) {
        this.faculties = faculties;
    }
}