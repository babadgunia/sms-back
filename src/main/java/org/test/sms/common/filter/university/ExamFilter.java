package org.test.sms.common.filter.university;

import org.test.sms.common.entity.university.Building;
import org.test.sms.common.entity.university.Course;
import org.test.sms.common.entity.university.Faculty;
import org.test.sms.common.enums.university.ExamType;
import org.test.sms.common.filter.general.AbstractFilter;

import java.time.LocalDateTime;

public class ExamFilter extends AbstractFilter {

    private Faculty faculty;

    private Course course;

    private LocalDateTime fromDate;

    private LocalDateTime toDate;

    private Building building;

    private ExamType type;

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public LocalDateTime getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDateTime fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDateTime getToDate() {
        return toDate;
    }

    public void setToDate(LocalDateTime toDate) {
        this.toDate = toDate;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public ExamType getType() {
        return type;
    }

    public void setType(ExamType type) {
        this.type = type;
    }
}