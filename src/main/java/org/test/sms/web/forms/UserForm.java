package org.test.sms.web.forms;

import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.ui.FormLayout;
import org.test.sms.common.entities.User;
import org.test.sms.common.entities.UserGroup;
import org.test.sms.common.enums.Translations;
import org.test.sms.common.exception.AppException;
import org.test.sms.common.utils.SecurityUtils;
import org.test.sms.common.utils.Utils;
import org.test.sms.web.customComponents.CComboBox;
import org.test.sms.web.customComponents.CPasswordField;
import org.test.sms.web.customComponents.CTextField;
import org.test.sms.web.utils.UIUtils;

import java.util.Objects;

public class UserForm extends GenericForm<User> {

    private CTextField usernameField;

    private CPasswordField passwordField;

    private CComboBox<UserGroup> userGroupBox;

    @Override
    protected void initFormContent(FormLayout layout) {
        initNameBox(layout, (Objects.nonNull(entity) ? entity.getName() : null));

        usernameField = new CTextField(UIUtils.getTranslation(Translations.USERNAME));
        passwordField = new CPasswordField(UIUtils.getTranslation(Translations.PASSWORD));

        userGroupBox = new CComboBox<>(UIUtils.getTranslation(Translations.USERGROUP));
        cachingService.getUserGroups(null).forEach(e -> {
            userGroupBox.addItem(e);
            userGroupBox.setItemCaption(e, UIUtils.getTextValue(e.getName()));
        });

        if (!isAdd) {
            usernameField.setEnabled(false);
            usernameField.setValue(entity.getUsername());
            userGroupBox.select(entity.getUserGroup());
        }

        layout.addComponents(usernameField, userGroupBox, passwordField);
    }

    @Override
    protected void save() {
        try {
            nameBox.validate();
            usernameField.validate();
            userGroupBox.validate();

            if (isAdd) {
                passwordField.validate();
            }
        } catch (InvalidValueException e) {
            nameBox.setValidationVisible(true);
            usernameField.setValidationVisible(true);
            userGroupBox.setValidationVisible(true);

            if (isAdd) {
                passwordField.setValidationVisible(true);
            }

            errorLabel.setValue(UIUtils.getTranslation(Translations.FILL_ALL_FIELDS));
            errorLabel.setVisible(true);

            return;
        }

        if (isAdd) {
            entity = new User();
        }

        entity.setName(nameBox.getValue());
        entity.setUsername(usernameField.getValue().trim());
        entity.setUserGroup(userGroupBox.getValue());

        if (!Utils.isBlank(passwordField.getValue())) {
            entity.setPassword(SecurityUtils.encryptPassword(passwordField.getValue()));
        }

        try {
            entity = isAdd ? manager.add(entity) : manager.update(entity);

            if (isAdd) {
                container.addItem(entity);
            } else {
                int index = container.indexOfId(entity);
                container.removeItem(entity);
                container.addItemAt(index, entity);
            }

            UIUtils.showSuccessNotification();

            close();
        } catch (AppException e) {
            errorLabel.setValue(UIUtils.getTranslation(e));
            errorLabel.setVisible(true);
        }
    }
}