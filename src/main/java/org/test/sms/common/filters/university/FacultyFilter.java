package org.test.sms.common.filters.university;

import org.test.sms.common.filters.AbstractFilter;

import java.util.List;

public class FacultyFilter extends AbstractFilter {

    private List<String> names;

    private List<String> courses;

    public List<String> getNames() {
        return names;
    }

    public void setNames(List<String> names) {
        this.names = names;
    }

    public List<String> getCourses() {
        return courses;
    }

    public void setCourses(List<String> courses) {
        this.courses = courses;
    }
}