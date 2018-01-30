package org.test.sms.server.service.general;

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
import org.test.sms.common.service.general.CachingService;
import org.test.sms.common.service.general.TextService;
import org.test.sms.common.service.general.UserGroupService;
import org.test.sms.common.service.university.BuildingService;
import org.test.sms.common.service.university.CourseService;
import org.test.sms.common.service.university.FacultyService;
import org.test.sms.common.service.university.LecturerService;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CachingServiceImpl implements CachingService {

    private BuildingService buildingService;

    private CourseService courseService;

    private FacultyService facultyService;

    private LecturerService lecturerService;

    private TextService textService;

    private UserGroupService userGroupService;

    private List<Building> buildings = new ArrayList<>();

    private List<Course> courses = new ArrayList<>();

    private List<Faculty> faculties = new ArrayList<>();

    private List<Lecturer> lecturers = new ArrayList<>();

    private List<Text> texts = new ArrayList<>();

    private List<UserGroup> userGroups = new ArrayList<>();

    @PostConstruct
    private void init() {
        reload();
    }

    @Override
    public void reload() {
        buildings = buildingService.getList(null);
        courses = courseService.getList(null);
        faculties = facultyService.getList(null);
        lecturers = lecturerService.getList(null);
        texts = textService.getList(null);
        userGroups = userGroupService.getList(null);
    }

    @Override
    public List<Building> getBuildings() {
        return buildings;
    }

    @Override
    public List<Faculty> getFaculties(FacultyFilter filter) {
        if (Objects.nonNull(filter)) {
            Stream<Faculty> stream = faculties.stream();

            List<String> names = filter.getNames();
            if (Objects.nonNull(names)) {
                stream = stream.filter(e -> names.contains(e.getName()));
            }

            List<String> courses = filter.getCourses();
            if (Objects.nonNull(courses)) {
                stream = stream.filter(e -> e.getCourses().stream().filter(f -> courses.contains(f.getName())).count() != 0);
            }

            return stream.collect(Collectors.toList());
        }

        return faculties;
    }

    @Override
    public List<Course> getCourses(CourseFilter filter) {
        if (Objects.nonNull(filter)) {
            Stream<Course> stream = courses.stream();

            List<String> names = filter.getNames();
            if (Objects.nonNull(names)) {
                stream = stream.filter(e -> names.contains(e.getName()));
            }

            List<String> faculties = filter.getFaculties();
            if (Objects.nonNull(faculties)) {
                stream = stream.filter(e -> {
                    Faculty faculty = e.getFaculty();

                    return Objects.nonNull(faculty) && faculties.contains(faculty.getName());
                });
            }

            return stream.collect(Collectors.toList());
        }

        return courses;
    }

    @Override
    public List<Lecturer> getLecturers(LecturerFilter filter) {
        if (Objects.nonNull(filter)) {
            Stream<Lecturer> stream = lecturers.stream();

            List<String> firstNames = filter.getFirstNames();
            if (Objects.nonNull(firstNames)) {
                stream = stream.filter(e -> firstNames.contains(e.getFirstName()));
            }

            List<String> lastNames = filter.getLastNames();
            if (Objects.nonNull(lastNames)) {
                stream = stream.filter(e -> lastNames.contains(e.getLastName()));
            }

            String personalNumber = filter.getPersonalNumber();
            if (Objects.nonNull(personalNumber)) {
                stream = stream.filter(e -> e.getPersonalNumber().contains(personalNumber));
            }

            return stream.collect(Collectors.toList());
        }

        return lecturers;
    }

    @Override
    public List<Text> getTexts(TextFilter filter) {
        return Objects.nonNull(filter) ? texts.stream().filter(e -> e.getKey().contains(filter.getKey().toUpperCase())).collect(Collectors.toList()) : texts;
    }

    @Override
    public List<UserGroup> getUserGroups(UserGroupFilter filter) {
        if (Objects.nonNull(filter)) {
            Stream<UserGroup> stream = userGroups.stream();

            List<String> names = filter.getNames();
            if (Objects.nonNull(names)) {
                stream = stream.filter(e -> names.contains(e.getName()));
            }

            return stream.collect(Collectors.toList());
        }

        return userGroups;
    }
}