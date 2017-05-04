package org.test.sms.web.tabs;

import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import org.test.sms.common.entities.university.Lecturer;
import org.test.sms.common.enums.TabType;
import org.test.sms.common.enums.Translations;
import org.test.sms.common.filters.LecturerFilter;
import org.test.sms.common.service.university.LecturerService;
import org.test.sms.common.utils.Utils;
import org.test.sms.web.customComponents.CSearchTextField;
import org.test.sms.web.forms.LecturerForm;
import org.test.sms.web.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class LecturerTab extends GenericTab<Lecturer> {

    private LecturerService service;

    private LecturerForm form;

    private CSearchTextField firstNameField;

    private CSearchTextField lastNameField;

    private CSearchTextField personalNumberField;

    public LecturerTab init() {
        return (LecturerTab) super.init(TabType.LECTURER, Lecturer.class, service, form);
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
    protected String getButtonColumns(Map<String, Consumer<Lecturer>> buttonActions) {
        String photo = "photo";
        buttonActions.put(photo, this::showImage);

        return photo + ", ";
    }

    private void showImage(Lecturer entity) {
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
    protected void update(Lecturer entity) {
        form.init(tabType, service, service.get(entity.getId()).get(), container);
    }

    @Override
    protected void delete(Lecturer entity) {
        UIUtils.showConfirmDialog(entity.getFirstName() + " " + entity.getLastName(), service, entity, container, null);
    }

    @Override
    protected void performAdditionalReloadActions() {
    }

    @Override
    protected List<Lecturer> getEntityList() {
        LecturerFilter filter = new LecturerFilter();

        List<Lecturer> lecturers = cachingService.getLecturers(null);

        List<String> firstNames = null;
        String firstName = firstNameField.getValue();
        if (!Utils.isBlank(firstName)) {
            List<String> lecturerFirstNames = lecturers.parallelStream().map(Lecturer::getFirstName).collect(Collectors.toList());
            firstNames = UIUtils.getTextKeys(lecturerFirstNames, firstName);
        }

        if (firstNames != null && firstNames.isEmpty()) {
            return new ArrayList<>();
        }

        filter.setFirstNames(firstNames);

        List<String> lastNames = null;
        String lastName = lastNameField.getValue();
        if (!Utils.isBlank(lastName)) {
            List<String> lecturerLastNames = lecturers.parallelStream().map(Lecturer::getLastName).collect(Collectors.toList());
            lastNames = UIUtils.getTextKeys(lecturerLastNames, lastName);
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

        return cachingService.getLecturers(filter);
    }
}