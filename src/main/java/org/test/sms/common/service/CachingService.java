package org.test.sms.common.service;

import org.test.sms.common.entities.general.Text;
import org.test.sms.common.entities.general.UserGroup;
import org.test.sms.common.entities.university.Building;
import org.test.sms.common.entities.university.Course;
import org.test.sms.common.entities.university.Faculty;
import org.test.sms.common.entities.university.Lecturer;
import org.test.sms.common.filters.general.TextFilter;
import org.test.sms.common.filters.general.UserGroupFilter;
import org.test.sms.common.filters.university.CourseFilter;
import org.test.sms.common.filters.university.FacultyFilter;
import org.test.sms.common.filters.university.LecturerFilter;

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