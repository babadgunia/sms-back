package org.test.sms.common.service;

import org.test.sms.common.entities.Text;
import org.test.sms.common.entities.UserGroup;
import org.test.sms.common.entities.university.Building;
import org.test.sms.common.entities.university.Course;
import org.test.sms.common.entities.university.Faculty;
import org.test.sms.common.entities.university.Lecturer;
import org.test.sms.common.filters.CourseFilter;
import org.test.sms.common.filters.FacultyFilter;
import org.test.sms.common.filters.LecturerFilter;
import org.test.sms.common.filters.TextFilter;
import org.test.sms.common.filters.UserGroupFilter;

import java.util.List;

public interface CachingService {

    void reload();

    List<Building> getBuildings();

    List<Course> getCourses(CourseFilter filter);

    List<Faculty> getFaculties(FacultyFilter filter);

    List<Lecturer> getLecturers(LecturerFilter filter);

    List<Text> getTexts(TextFilter filter);

    List<UserGroup> getUserGroups(UserGroupFilter filter);
}