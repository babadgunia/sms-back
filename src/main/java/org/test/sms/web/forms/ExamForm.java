package org.test.sms.web.forms;

import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.PopupDateField;
import org.test.sms.common.entities.university.Building;
import org.test.sms.common.entities.university.Course;
import org.test.sms.common.entities.university.Exam;
import org.test.sms.common.entities.university.Faculty;
import org.test.sms.common.enums.Translations;
import org.test.sms.common.enums.university.ExamType;
import org.test.sms.common.exception.AppException;
import org.test.sms.common.utils.DateUtils;
import org.test.sms.web.customComponents.CComboBox;
import org.test.sms.web.customComponents.CPopupDateField;
import org.test.sms.web.customComponents.CTextField;
import org.test.sms.web.utils.UIUtils;

import java.util.Arrays;
import java.util.List;

public class ExamForm extends GenericForm<Exam> {

    private CComboBox<Faculty> facultyBox;

    private CComboBox<Course> courseBox;

    private PopupDateField startDateField;

    private PopupDateField endDateField;

    private CComboBox<Building> buildingBox;

    private CComboBox<ExamType> typeBox;

    private CTextField maxGradeField;

    private CTextField numStudentsField;

    @Override
    protected void initFormContent(FormLayout layout) {
        List<Faculty> faculties = cachingService.getFaculties(null);

        facultyBox = new CComboBox<>(UIUtils.getTranslation(Translations.FACULTY));
        faculties.forEach(e -> {
            facultyBox.addItem(e);
            facultyBox.setItemCaption(e, UIUtils.getTextValue(e.getName()));
        });

        courseBox = new CComboBox<>(UIUtils.getTranslation(Translations.COURSE));

        facultyBox.addValueChangeListener(e -> {
            courseBox.removeAllItems();

            Faculty faculty = facultyBox.getValue();
            faculty.getCourses().forEach(f -> {
                courseBox.addItem(f);
                courseBox.setItemCaption(f, UIUtils.getTextValue(f.getName()));
            });
        });

        startDateField = new CPopupDateField(UIUtils.getTranslation(Translations.START_DATE));
        startDateField.setResolution(Resolution.MINUTE);

        endDateField = new CPopupDateField(UIUtils.getTranslation(Translations.END_DATE));
        endDateField.setResolution(Resolution.MINUTE);

        List<Building> buildings = cachingService.getBuildings();

        buildingBox = new CComboBox<>(UIUtils.getTranslation(Translations.BUILDING));
        buildings.forEach(e -> {
            buildingBox.addItem(e);
            buildingBox.setItemCaption(e, UIUtils.getTextValue(e.getName()));
        });

        typeBox = new CComboBox<>(UIUtils.getTranslation(Translations.TYPE));
        typeBox.setNullSelectionAllowed(false);

        Arrays.stream(ExamType.values()).forEach(e -> {
            typeBox.addItem(e);
            typeBox.setItemCaption(e, UIUtils.getTranslation(e));
        });

        maxGradeField = new CTextField(UIUtils.getTranslation(Translations.MAX_GRADE));
        numStudentsField = new CTextField(UIUtils.getTranslation(Translations.NUM_STUDENTS));

        if (!isAdd) {
            Course course = entity.getCourse();

            facultyBox.setValue(faculties.get(faculties.indexOf(course.getFaculty())));

            courseBox.addItem(course);
            courseBox.setItemCaption(course, UIUtils.getTextValue(course.getName()));
            courseBox.setValue(course);

            startDateField.setValue(DateUtils.converToDate(entity.getStartDate()));
            endDateField.setValue(DateUtils.converToDate(entity.getEndDate()));
            buildingBox.setValue(entity.getBuilding());
            typeBox.setValue(entity.getType());
            maxGradeField.setValue(entity.getMaxGrade().toString());
            numStudentsField.setValue(entity.getNumStudents().toString());
        }

        layout.addComponents(facultyBox, courseBox, startDateField, endDateField, buildingBox, typeBox, maxGradeField, numStudentsField);
    }

    @Override
    protected void save() {
        try {
            courseBox.validate();
            startDateField.validate();
            endDateField.validate();
            buildingBox.validate();
            typeBox.validate();

            UIUtils.validate(maxGradeField, numStudentsField);
        } catch (InvalidValueException e) {
            courseBox.setValidationVisible(true);
            startDateField.setValidationVisible(true);
            endDateField.setValidationVisible(true);
            buildingBox.setValidationVisible(true);
            typeBox.setValidationVisible(true);

            UIUtils.setValidationVisible(maxGradeField, numStudentsField);

            errorLabel.setValue(UIUtils.getTranslation(Translations.FILL_ALL_FIELDS));
            errorLabel.setVisible(true);

            return;
        }

        if (isAdd) {
            entity = new Exam();
        }

        entity.setCourse(courseBox.getValue());
        entity.setStartDate(DateUtils.convertToLocalDateTime(startDateField.getValue()));
        entity.setEndDate(DateUtils.convertToLocalDateTime(endDateField.getValue()));
        entity.setBuilding(buildingBox.getValue());
        entity.setType(typeBox.getValue());
        entity.setMaxGrade(Integer.parseInt(maxGradeField.getValue()));
        entity.setNumStudents(Integer.parseInt(numStudentsField.getValue()));

        try {
            entity = isAdd ? manager.add(entity) : manager.update(entity);

            if (isAdd) {
                container.addItem(entity);
            } else {
                int index = container.indexOfId(entity);

                container.removeItem(entity);
                container.addItemAt(index, entity);
            }

            List<Exam> list = manager.getList(null);

            if (isAdd) {
                list.add(entity);
            } else {
                list.set(list.indexOf(entity), entity);
            }

            UIUtils.showSuccessNotification();

            close();
        } catch (AppException e) {
            errorLabel.setValue(UIUtils.getTranslation(e));
            errorLabel.setVisible(true);
        }
    }
}