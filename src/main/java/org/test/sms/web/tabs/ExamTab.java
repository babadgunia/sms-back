package org.test.sms.web.tabs;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import org.test.sms.common.entities.university.Building;
import org.test.sms.common.entities.university.Course;
import org.test.sms.common.entities.university.Exam;
import org.test.sms.common.entities.university.Faculty;
import org.test.sms.common.enums.TabType;
import org.test.sms.common.enums.Translations;
import org.test.sms.common.enums.university.ExamType;
import org.test.sms.common.filters.university.ExamFilter;
import org.test.sms.common.service.university.ExamService;
import org.test.sms.common.utils.DateUtils;
import org.test.sms.web.customComponents.CSearchComboBox;
import org.test.sms.web.customComponents.CSearchPopupDateField;
import org.test.sms.web.forms.ExamForm;
import org.test.sms.web.utils.HTMLClasses;
import org.test.sms.web.utils.SearchFieldListener;
import org.test.sms.web.utils.UIUtils;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

public class ExamTab extends GenericTab<Exam> {

    private ExamService service;

    private ExamForm form;

    private CSearchComboBox<Faculty> facultyBox;

    private CSearchComboBox<Course> courseBox;

    private CSearchPopupDateField fromDateField;

    private CSearchPopupDateField toDateField;

    private CSearchComboBox<Building> buildingBox;

    private CSearchComboBox<ExamType> typeBox;

    public ExamTab init() {
        return (ExamTab) super.init(TabType.EXAM, Exam.class, service, form);
    }

    @Override
    protected void initSearchFilterContent(HorizontalLayout layout) {
        Panel facultyPanel = new Panel();

        facultyPanel.setSizeUndefined();
        facultyPanel.addShortcutListener(new SearchFieldListener(this::reload));
        facultyPanel.addStyleName(HTMLClasses.NO_BORDER.getClassName());

        facultyBox = new CSearchComboBox<>();
        facultyBox.setInputPrompt(UIUtils.getTranslation(Translations.FACULTY));

        cachingService.getFaculties(null).forEach(e -> {
            facultyBox.addItem(e);
            facultyBox.setItemCaption(e, UIUtils.getTextValue(e.getName()));
        });

        facultyPanel.setContent(facultyBox);

        Panel coursePanel = new Panel();

        coursePanel.setSizeUndefined();
        coursePanel.addShortcutListener(new SearchFieldListener(this::reload));
        coursePanel.addStyleName(HTMLClasses.NO_BORDER.getClassName());

        courseBox = new CSearchComboBox<>();
        courseBox.setInputPrompt(UIUtils.getTranslation(Translations.COURSE));

        cachingService.getCourses(null).forEach(e -> {
            courseBox.addItem(e);
            courseBox.setItemCaption(e, UIUtils.getTextValue(e.getName()));
        });

        coursePanel.setContent(courseBox);

        fromDateField = new CSearchPopupDateField(UIUtils.getTranslation(Translations.FROM));
        toDateField = new CSearchPopupDateField(UIUtils.getTranslation(Translations.TO));

        buildingBox = new CSearchComboBox<>();
        buildingBox.setInputPrompt(UIUtils.getTranslation(Translations.BUILDING));

        cachingService.getBuildings().forEach(e -> {
            buildingBox.addItem(e);
            buildingBox.setItemCaption(e, UIUtils.getTextValue(e.getName()));
        });

        typeBox = new CSearchComboBox<>();
        typeBox.setInputPrompt(UIUtils.getTranslation(Translations.TYPE));

        Arrays.stream(ExamType.values()).forEach(e -> {
            typeBox.addItem(e);
            typeBox.setItemCaption(e, UIUtils.getTranslation(e));
        });

        layout.addComponents(facultyPanel, coursePanel, fromDateField, toDateField, buildingBox, typeBox);
    }

    @Override
    protected String getEntityColumns() {
        return "type, startDate, endDate, maxGrade, numStudents";
    }

    @Override
    protected String getNestedEntityColumns() {
        return "course.name, building.name";
    }

    @Override
    protected String getButtonColumns(Map<String, Consumer<Exam>> buttonActions) {
        return "";
    }

    @Override
    protected String getTextConverterColumns() {
        return "course.name, building.name";
    }

    @Override
    protected String getTranslationConverterColumns() {
        return "type";
    }

    @Override
    protected String getColumnOrder() {
        return "course.name, startDate, endDate, building.name, type, maxGrade, numStudents";
    }

    @Override
    protected void update(Exam entity) {
        form.init(tabType, service, service.get(entity.getId()).get(), container);
    }

    @Override
    protected void delete(Exam entity) {
        UIUtils.showConfirmDialog(UIUtils.getTextValue(entity.getCourse().getName()) + "(" + UIUtils.getTranslation(entity.getType()) + ")", service, entity, container, null);
    }

    @Override
    protected void performAdditionalReloadActions() {
    }

    @Override
    protected List<Exam> getEntityList() {
        ExamFilter filter = new ExamFilter();

        filter.setFaculty(facultyBox.getValue());
        filter.setCourse(courseBox.getValue());

        Date fromDate = fromDateField.getValue();
        if (Objects.nonNull(fromDate)) {
            filter.setFromDate(DateUtils.convertToLocalDateTime(fromDate));
        }

        Date toDate = toDateField.getValue();
        if (Objects.nonNull(toDate)) {
            filter.setToDate(DateUtils.convertToLocalDateTime(toDate));
        }

        filter.setBuilding(buildingBox.getValue());
        filter.setType(typeBox.getValue());

        return service.getList(filter);
    }
}