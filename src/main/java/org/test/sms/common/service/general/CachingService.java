package org.test.sms.common.service.general;

import org.test.sms.common.entity.general.Text;
import org.test.sms.common.entity.general.UserGroup;
import org.test.sms.common.entity.university.Building;
import org.test.sms.common.entity.university.Course;
import org.test.sms.common.entity.university.Faculty;
import org.test.sms.common.entity.university.Lecturer;
import org.test.sms.common.filter.general.TextFilter;
import org.test.sms.common.filter.general.UserGroupFilter;
import org.test.sms.common.filter.university.CourseFilter;
import org.test.sms.common.filter.university.FacultyFilter;
import org.test.sms.common.filter.university.LecturerFilter;

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