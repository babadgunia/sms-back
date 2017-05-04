package org.test.sms.web.tabs;

import com.vaadin.ui.HorizontalLayout;
import org.test.sms.common.entities.university.Course;
import org.test.sms.common.entities.university.Faculty;
import org.test.sms.common.enums.TabType;
import org.test.sms.common.enums.Translations;
import org.test.sms.common.filters.FacultyFilter;
import org.test.sms.common.service.university.FacultyService;
import org.test.sms.common.utils.Utils;
import org.test.sms.web.customComponents.CSearchTextField;
import org.test.sms.web.forms.FacultyForm;
import org.test.sms.web.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class FacultyTab extends GenericTab<Faculty> {

    private FacultyService service;

    private FacultyForm form;

    private CSearchTextField nameField;

    private CSearchTextField courseField;

    public FacultyTab init() {
        return (FacultyTab) super.init(TabType.FACULTY, Faculty.class, service, form);
    }

    @Override
    protected void initSearchFilterContent(HorizontalLayout layout) {
        nameField = new CSearchTextField(this::reload);
        nameField.setInputPrompt(UIUtils.getTranslation(Translations.NAME));

        courseField = new CSearchTextField(this::reload);
        courseField.setInputPrompt(UIUtils.getTranslation(Translations.COURSE));

        layout.addComponents(nameField, courseField);
    }

    @Override
    protected String getEntityColumns() {
        return "name";
    }

    @Override
    protected String getNestedEntityColumns() {
        return "";
    }

    @Override
    protected String getButtonColumns(Map<String, Consumer<Faculty>> buttonActions) {
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
        return "name";
    }

    @Override
    protected void update(Faculty entity) {
        form.init(tabType, service, service.get(entity.getId()).get(), container);
    }

    @Override
    protected void delete(Faculty entity) {
        UIUtils.showConfirmDialog(entity.getName(), service, entity, container, cachingService.getFaculties(null));
    }

    @Override
    protected void performAdditionalReloadActions() {
    }

    @Override
    protected List<Faculty> getEntityList() {
        FacultyFilter filter = new FacultyFilter();

        List<String> names = null;
        String name = nameField.getValue();
        if (!Utils.isBlank(name)) {
            List<String> facultyNames = cachingService.getFaculties(null).parallelStream().map(Faculty::getName).collect(Collectors.toList());
            names = UIUtils.getTextKeys(facultyNames, name);
        }

        if (names != null && names.isEmpty()) {
            return new ArrayList<>();
        }
        filter.setNames(names);

        List<String> courses = null;
        String course = courseField.getValue();
        if (!Utils.isBlank(course)) {
            List<String> courseNames = cachingService.getCourses(null).parallelStream().map(Course::getName).collect(Collectors.toList());
            courses = UIUtils.getTextKeys(courseNames, course);
        }

        if (courses != null && courses.isEmpty()) {
            return new ArrayList<>();
        }
        filter.setCourses(courses);

        return cachingService.getFaculties(filter);
    }
}