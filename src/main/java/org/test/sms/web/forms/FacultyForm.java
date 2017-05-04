package org.test.sms.web.forms;

import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TwinColSelect;
import org.test.sms.common.entities.university.Course;
import org.test.sms.common.entities.university.Faculty;
import org.test.sms.common.enums.Translations;
import org.test.sms.common.exception.AppException;
import org.test.sms.common.service.university.CourseService;
import org.test.sms.web.utils.HTMLClasses;
import org.test.sms.web.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class FacultyForm extends GenericForm<Faculty> {

    private CourseService courseService;

    private TwinColSelect courseSelect;

    @Override
    protected void initFormContent(FormLayout layout) {
        initNameBox(layout, (Objects.nonNull(entity) ? entity.getName() : null));

        courseSelect = new TwinColSelect(UIUtils.getTranslation(Translations.COURSES));
        courseSelect.addStyleName(HTMLClasses.CUSTOM_TWIN_COL_SELECT.getClassName());

        cachingService.getCourses(null).forEach(e -> {
            courseSelect.addItem(e);
            courseSelect.setItemCaption(e, UIUtils.getTextValue(e.getName()));
        });

        if (!isAdd) {
            courseSelect.setValue(entity.getCourses());
        }

        layout.addComponent(courseSelect);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void save() {
        try {
            nameBox.validate();
        } catch (InvalidValueException e) {
            nameBox.setValidationVisible(true);

            errorLabel.setValue(UIUtils.getTranslation(Translations.FILL_ALL_FIELDS));
            errorLabel.setVisible(true);

            return;
        }

        if (isAdd) {
            entity = new Faculty();
        }

        entity.setName(nameBox.getValue());

        List<Course> oldCourses = entity.getCourses();
        List<Course> newCourses = new ArrayList<>();

        ((Set<Course>) courseSelect.getValue()).forEach(e -> {
            e.setFaculty(entity);
            newCourses.add(e);
        });

        entity.setCourses(newCourses);

        try {
            entity = isAdd ? manager.add(entity) : manager.update(entity);

            List<Course> courseList = cachingService.getCourses(null);

            if (isAdd) {
                newCourses.parallelStream().forEach(e -> {
                    e = updateCourse(e);

                    courseList.set(courseList.indexOf(e), e);
                });
            }

            oldCourses.parallelStream().forEach(e -> {
                if (!newCourses.contains(e)) {
                    e.setFaculty(null);
                    e = updateCourse(e);

                    courseList.set(courseList.indexOf(e), e);
                }
            });

            if (isAdd) {
                container.addItem(entity);
            } else {
                int index = container.indexOfId(entity);
                container.removeItem(entity);
                container.addItemAt(index, entity);
            }

            List<Faculty> facultyList = cachingService.getFaculties(null);
            if (isAdd) {
                facultyList.add(entity);
            } else {
                facultyList.set(facultyList.indexOf(entity), entity);
            }

            UIUtils.showSuccessNotification();

            close();
        } catch (AppException e) {
            errorLabel.setValue(UIUtils.getTranslation(e));
            errorLabel.setVisible(true);
        }
    }

    private Course updateCourse(Course course) {
        try {
            return courseService.update(course);
        } catch (AppException e) {
            errorLabel.setValue(UIUtils.getTranslation(e));
            errorLabel.setVisible(true);

            return null;
        }
    }
}