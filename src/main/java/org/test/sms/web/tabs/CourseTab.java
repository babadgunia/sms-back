package org.test.sms.web.tabs;

import com.vaadin.ui.HorizontalLayout;
import org.test.sms.common.entities.university.Course;
import org.test.sms.common.entities.university.Faculty;
import org.test.sms.common.enums.TabType;
import org.test.sms.common.enums.Translations;
import org.test.sms.common.filters.university.CourseFilter;
import org.test.sms.common.service.university.CourseService;
import org.test.sms.common.utils.Utils;
import org.test.sms.web.customComponents.CSearchTextField;
import org.test.sms.web.forms.CourseForm;
import org.test.sms.web.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class CourseTab extends GenericTab<Course> {

    private CourseService service;

    private CourseForm form;

    private CSearchTextField nameField;

    private CSearchTextField facultyField;

    public CourseTab init() {
        return (CourseTab) super.init(TabType.COURSE, Course.class, service, form);
    }

    @Override
    protected void initSearchFilterContent(HorizontalLayout layout) {
        nameField = new CSearchTextField(this::reload);
        nameField.setInputPrompt(UIUtils.getTranslation(Translations.NAME));

        facultyField = new CSearchTextField(this::reload);
        facultyField.setInputPrompt(UIUtils.getTranslation(Translations.FACULTY));

        layout.addComponents(nameField, facultyField);
    }

    @Override
    protected String getEntityColumns() {
        return "name, numCredits, maxStudents";
    }

    @Override
    protected String getNestedEntityColumns() {
        return "";
    }

    @Override
    protected String getButtonColumns(Map<String, Consumer<Course>> buttonActions) {
        return "";
    }

    @Override
    protected String getTextConverterColumns() {
        return "name";
    }

    @Override
    protected String getTranslationConverterColumns() {
        return "";
    }

    @Override
    protected String getColumnOrder() {
        return "name, numCredits, maxStudents";
    }

    @Override
    protected void update(Course entity) {
        form.init(tabType, service, service.get(entity.getId()).get(), container);
    }

    @Override
    protected void delete(Course entity) {
        UIUtils.showConfirmDialog(entity.getName(), service, entity, container, cachingService.getCourses(null));
    }

    @Override
    protected void performAdditionalReloadActions() {
    }

    @Override
    protected List<Course> getEntityList() {
        CourseFilter filter = new CourseFilter();

        List<String> names = null;
        String name = nameField.getValue();
        if (!Utils.isBlank(name)) {
            List<String> courseNames = cachingService.getCourses(null).parallelStream().map(Course::getName).collect(Collectors.toList());
            names = UIUtils.getTextKeys(courseNames, name);
        }

        if (names != null && names.isEmpty()) {
            return new ArrayList<>();
        }
        filter.setNames(names);

        List<String> faculties = null;
        String faculty = facultyField.getValue();
        if (!Utils.isBlank(faculty)) {
            List<String> facultyNames = cachingService.getFaculties(null).parallelStream().map(Faculty::getName).collect(Collectors.toList());
            faculties = UIUtils.getTextKeys(facultyNames, faculty);
        }

        if (faculties != null && faculties.isEmpty()) {
            return new ArrayList<>();
        }
        filter.setFaculties(faculties);

        return cachingService.getCourses(filter);
    }
}