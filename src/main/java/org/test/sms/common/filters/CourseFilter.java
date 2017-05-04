package org.test.sms.common.filters;

import org.test.sms.common.entities.university.Faculty;

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