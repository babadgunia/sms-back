package org.test.sms.web.forms;

import com.kbdunn.vaadin.addons.fontawesome.FontAwesome;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.JavaScript;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import org.test.sms.common.entities.university.Course;
import org.test.sms.common.entities.university.Faculty;
import org.test.sms.common.entities.university.Module;
import org.test.sms.common.enums.TabPermissionType;
import org.test.sms.common.enums.Translations;
import org.test.sms.common.exception.AppException;
import org.test.sms.web.customComponents.CComboBox;
import org.test.sms.web.customComponents.CTextField;
import org.test.sms.web.utils.HTMLClasses;
import org.test.sms.web.utils.JSFunctions;
import org.test.sms.web.utils.UIUtils;

import java.util.List;
import java.util.Objects;

public class CourseForm extends GenericForm<Course> {

    private ModuleForm moduleForm;

    private CComboBox<Faculty> facultyBox;

    private CTextField numCreditsField;

    private CTextField maxStudentsField;

    private Accordion moduleAccordion;

    @Override
    protected void initFormContent(FormLayout layout) {
        initNameBox(layout, (Objects.nonNull(entity) ? entity.getName() : null));

        facultyBox = new CComboBox<>(UIUtils.getTranslation(Translations.FACULTY));
        cachingService.getFaculties(null).forEach(e -> {
            facultyBox.addItem(e);
            facultyBox.setItemCaption(e, UIUtils.getTextValue(e.getName()));
        });

        numCreditsField = new CTextField(UIUtils.getTranslation(Translations.NUM_CREDITS));
        maxStudentsField = new CTextField(UIUtils.getTranslation(Translations.MAX_STUDENTS));

        moduleAccordion = new Accordion();
        moduleAccordion.setCaption(UIUtils.getTranslation(Translations.MODULES));
        moduleAccordion.addStyleName(HTMLClasses.CUSTOM_ACCORDION.getClassName());

        boolean canEdit = UIUtils.hasPermission(tabType, TabPermissionType.EDIT);

        HorizontalLayout sacrificeLayout = new HorizontalLayout();

        if (!isAdd) {
            facultyBox.setValue(entity.getFaculty());
            numCreditsField.setValue(entity.getNumCredits().toString());
            maxStudentsField.setValue(entity.getMaxStudents().toString());

            for (Module module : entity.getModules()) {
                Panel accordionPanel = new Panel();

                accordionPanel.addStyleName(HTMLClasses.FULL_WIDTH.getClassName());
                accordionPanel.setHeight(UIUtils.ACCORDION_TAB_HEIGHT);

                VerticalLayout accordionLayout = new VerticalLayout();

                accordionLayout.addComponent(new Label(module.getDescription(), ContentMode.HTML));
                accordionLayout.setMargin(true);
                accordionPanel.setContent(accordionLayout);

                moduleAccordion.addTab(accordionPanel, UIUtils.getTextValue(module.getName()));

                if (canEdit) {
                    Button editModuleButton = new Button();

                    editModuleButton.setIcon(FontAwesome.EDIT);
                    editModuleButton.setStyleName(HTMLClasses.BUTTON_ON_ACCORDION.getClassName());
                    editModuleButton.addClickListener(e -> moduleForm.init(tabType, module, manager, entity));

                    sacrificeLayout.addComponent(editModuleButton);
                }
            }
        }

        layout.addComponents(facultyBox, numCreditsField, maxStudentsField, moduleAccordion);

        if (UIUtils.hasPermission(tabType, TabPermissionType.ADD)) {
            if (isAdd) {
                entity = new Course();
            }

            Button addModuleButton = new Button(UIUtils.getTranslation(Translations.ADD_MODULE), e -> moduleForm.init(tabType, null, manager, entity));

            buttonLayout.addComponent(addModuleButton, 0);
        }

        buttonLayout.addComponent(sacrificeLayout, 0);

        JavaScript.getCurrent().execute(JSFunctions.BUTTON_ON_ACCORDION.getJSFunction(HTMLClasses.CUSTOM_ACCORDION.getClassName(), HTMLClasses.BUTTON_ON_ACCORDION.getClassName()));
    }

    @Override
    protected void save() {
        try {
            nameBox.validate();
            facultyBox.validate();
            numCreditsField.validate();
            maxStudentsField.validate();
        } catch (InvalidValueException e) {
            nameBox.setValidationVisible(true);
            facultyBox.setValidationVisible(true);
            numCreditsField.setValidationVisible(true);
            maxStudentsField.setValidationVisible(true);

            errorLabel.setValue(UIUtils.getTranslation(Translations.FILL_ALL_FIELDS));
            errorLabel.setVisible(true);

            return;
        }

        entity.setName(nameBox.getValue());
        entity.setFaculty(facultyBox.getValue());
        entity.setNumCredits(Integer.parseInt(numCreditsField.getValue()));
        entity.setMaxStudents(Integer.parseInt(maxStudentsField.getValue()));

        try {
            entity = isAdd ? manager.add(entity) : manager.update(entity);

            if (isAdd) {
                container.addItem(entity);
            } else {
                int index = container.indexOfId(entity);

                container.removeItem(entity);
                container.addItemAt(index, entity);
            }

            List<Course> list = cachingService.getCourses(null);

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