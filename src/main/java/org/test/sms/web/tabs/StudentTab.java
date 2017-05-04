package org.test.sms.web.tabs;

import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import org.test.sms.common.entities.university.Student;
import org.test.sms.common.enums.TabType;
import org.test.sms.common.enums.Translations;
import org.test.sms.common.filters.university.StudentFilter;
import org.test.sms.common.service.university.StudentService;
import org.test.sms.common.utils.Utils;
import org.test.sms.web.customComponents.CSearchTextField;
import org.test.sms.web.forms.StudentForm;
import org.test.sms.web.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class StudentTab extends GenericTab<Student> {

    private StudentService service;

    private StudentForm form;

    private CSearchTextField firstNameField;

    private CSearchTextField lastNameField;

    private CSearchTextField personalNumberField;

    public StudentTab init() {
        return (StudentTab) super.init(TabType.STUDENT, Student.class, service, form);
    }

    @Override
    protected void initSearchFilterContent(HorizontalLayout layout) {
        firstNameField = new CSearchTextField(this::reload);
        firstNameField.setInputPrompt(UIUtils.getTranslation(Translations.FIRST_NAME));

        lastNameField = new CSearchTextField(this::reload);
        lastNameField.setInputPrompt(UIUtils.getTranslation(Translations.LAST_NAME));

        personalNumberField = new CSearchTextField(this::reload);
        personalNumberField.setInputPrompt(UIUtils.getTranslation(Translations.PERSONAL_NUMBER));

        layout.addComponents(firstNameField, lastNameField, personalNumberField);
    }

    @Override
    protected String getEntityColumns() {
        return "firstName, lastName, personalNumber, phoneNumber";
    }

    @Override
    protected String getNestedEntityColumns() {
        return "user.username";
    }

    @Override
    protected String getButtonColumns(Map<String, Consumer<Student>> buttonActions) {
        String photo = "photo";
        buttonActions.put(photo, this::showImage);

        return photo + ", ";
    }

    private void showImage(Student entity) {
        Window window = new Window();
        window.setModal(true);

        GridLayout layout = new GridLayout();
        Image image = UIUtils.toImage(service.get(entity.getId()).get().getPhoto());
        layout.addComponent(image);

        window.setContent(layout);

        UI.getCurrent().addWindow(window);
    }

    @Override
    protected String getTextConverterColumns() {
        return "firstName, lastName";
    }

    @Override
    protected String getTranslationConverterColumns() {
        return "";
    }

    @Override
    protected String getColumnOrder() {
        return "firstName, lastName, personalNumber, phoneNumber, user.username";
    }

    @Override
    protected void update(Student entity) {
        form.init(tabType, service, service.get(entity.getId()).get(), container);
    }

    @Override
    protected void delete(Student entity) {
        UIUtils.showConfirmDialog(entity.getFirstName() + " " + entity.getLastName(), service, entity, container, null);
    }

    @Override
    protected void performAdditionalReloadActions() {
    }

    @Override
    protected List<Student> getEntityList() {
        StudentFilter filter = new StudentFilter();

        List<Student> students = service.getList(null);

        List<String> firstNames = null;
        String firstName = firstNameField.getValue();
        if (!Utils.isBlank(firstName)) {
            List<String> studentFirstNames = students.parallelStream().map(Student::getFirstName).collect(Collectors.toList());
            firstNames = UIUtils.getTextKeys(studentFirstNames, firstName);
        }

        if (firstNames != null && firstNames.isEmpty()) {
            return new ArrayList<>();
        }
        filter.setFirstNames(firstNames);

        List<String> lastNames = null;
        String lastName = lastNameField.getValue();
        if (!Utils.isBlank(lastName)) {
            List<String> studentLastNames = students.parallelStream().map(Student::getLastName).collect(Collectors.toList());
            lastNames = UIUtils.getTextKeys(studentLastNames, lastName);
        }

        if (lastNames != null && lastNames.isEmpty()) {
            return new ArrayList<>();
        }
        filter.setLastNames(lastNames);

        String personalNumber = personalNumberField.getValue();
        if (Utils.isBlank(personalNumber)) {
            personalNumber = null;
        }
        filter.setPersonalNumber(personalNumber);

        return service.getList(filter);
    }
}