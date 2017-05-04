package org.test.sms.web.forms;

import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.ui.FormLayout;
import org.test.sms.common.entities.university.Auditorium;
import org.test.sms.common.entities.university.Building;
import org.test.sms.common.enums.Translations;
import org.test.sms.common.exception.AppException;
import org.test.sms.web.customComponents.CComboBox;
import org.test.sms.web.customComponents.CTextField;
import org.test.sms.web.utils.UIUtils;

import java.util.List;

public class AuditoriumForm extends GenericForm<Auditorium> {

    private CTextField nameField;

    private CComboBox<Building> buildingBox;

    @Override
    protected void initFormContent(FormLayout layout) {
        nameField = new CTextField(UIUtils.getTranslation(Translations.NAME));

        buildingBox = new CComboBox<>(UIUtils.getTranslation(Translations.BUILDING));
        cachingService.getBuildings().forEach(e -> {
            buildingBox.addItem(e);
            buildingBox.setItemCaption(e, UIUtils.getTextValue(e.getName()));
        });

        if (!isAdd) {
            nameField.setValue(entity.getName());
            buildingBox.select(entity.getBuilding());
        }

        layout.addComponents(nameField, buildingBox);
    }

    @Override
    protected void save() {
        try {
            nameField.validate();
            buildingBox.validate();
        } catch (InvalidValueException e) {
            nameField.setValidationVisible(true);
            buildingBox.setValidationVisible(true);

            errorLabel.setValue(UIUtils.getTranslation(Translations.FILL_ALL_FIELDS));
            errorLabel.setVisible(true);

            return;
        }

        if (isAdd) {
            entity = new Auditorium();
        }

        entity.setName(nameField.getValue());
        entity.setBuilding(buildingBox.getValue());

        try {
            entity = isAdd ? manager.add(entity) : manager.update(entity);

            if (isAdd) {
                container.addItem(entity);
            } else {
                int index = container.indexOfId(entity);

                container.removeItem(entity);
                container.addItemAt(index, entity);
            }

            List<Auditorium> list = manager.getList(null);
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