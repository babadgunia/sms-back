package org.test.sms.web.forms;

import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.server.Resource;
import com.vaadin.server.StreamResource;
import com.vaadin.ui.Component;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.FormLayout;
import org.test.sms.common.entities.general.User;
import org.test.sms.common.entities.general.UserGroup;
import org.test.sms.common.entities.university.Lecturer;
import org.test.sms.common.enums.Translations;
import org.test.sms.common.enums.university.GenderType;
import org.test.sms.common.exception.AppException;
import org.test.sms.common.utils.DateUtils;
import org.test.sms.common.utils.SecurityUtils;
import org.test.sms.common.utils.Utils;
import org.test.sms.web.customComponents.CComboBox;
import org.test.sms.web.customComponents.CPasswordField;
import org.test.sms.web.customComponents.CPopupDateField;
import org.test.sms.web.customComponents.CTextField;
import org.test.sms.web.utils.UIUtils;
import org.vaadin.easyuploads.UploadField;

import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.List;

public class LecturerForm extends GenericForm<Lecturer> {

    private CTextField usernameField;

    private CComboBox<String> firstNameBox;

    private CComboBox<String> lastNameBox;

    private CComboBox<UserGroup> userGroupBox;

    private CPasswordField passwordField;

    private CComboBox<GenderType> genderBox;

    private CPopupDateField birthdateField;

    private CTextField personalNumberField;

    private CTextField phoneNumberField;

    private CTextField addressField;

    private CTextField emailField;

    private CTextField uniEmailField;

    private UploadField upload;

    @Override
    protected void initFormContent(FormLayout layout) {
        usernameField = new CTextField(UIUtils.getTranslation(Translations.USERNAME));

        userGroupBox = new CComboBox<>(UIUtils.getTranslation(Translations.USERGROUP));
        cachingService.getUserGroups(null).forEach(e -> {
            userGroupBox.addItem(e);
            userGroupBox.setItemCaption(e, UIUtils.getTextValue(e.getName()));
        });

        passwordField = new CPasswordField(UIUtils.getTranslation(Translations.PASSWORD));

        firstNameBox = new CComboBox<>(UIUtils.getTranslation(Translations.FIRST_NAME));
        UIUtils.getTextKeys().forEach(e -> {
            firstNameBox.addItem(e);
            firstNameBox.setItemCaption(e, UIUtils.getTextValue(e));
        });

        lastNameBox = new CComboBox<>(UIUtils.getTranslation(Translations.LAST_NAME));
        UIUtils.getTextKeys().forEach(e -> {
            lastNameBox.addItem(e);
            lastNameBox.setItemCaption(e, UIUtils.getTextValue(e));
        });

        genderBox = new CComboBox<>(UIUtils.getTranslation(Translations.GENDER));
        genderBox.setNullSelectionAllowed(false);

        Arrays.stream(GenderType.values()).forEach(e -> {
            genderBox.addItem(e);
            genderBox.setItemCaption(e, UIUtils.getTranslation(e));
        });

        birthdateField = new CPopupDateField(UIUtils.getTranslation(Translations.BIRTH_DATE));

        personalNumberField = new CTextField(UIUtils.getTranslation(Translations.PERSONAL_NUMBER));
        phoneNumberField = new CTextField(UIUtils.getTranslation(Translations.PHONE_NUMBER));
        addressField = new CTextField(UIUtils.getTranslation(Translations.ADDRESS));
        emailField = new CTextField(UIUtils.getTranslation(Translations.EMAIL));
        uniEmailField = new CTextField(UIUtils.getTranslation(Translations.UNIVERSITY_EMAIL));

        initUpload();

        if (!isAdd) {
            usernameField.setValue(entity.getUser().getUsername());
            userGroupBox.select(entity.getUser().getUserGroup());
            firstNameBox.setValue(entity.getFirstName());
            lastNameBox.setValue(entity.getLastName());
            genderBox.setValue(entity.getGender());
            birthdateField.setValue(DateUtils.convertToDate(entity.getBirthDate()));
            personalNumberField.setValue(entity.getPersonalNumber());
            phoneNumberField.setValue(entity.getPhoneNumber());
            addressField.setValue(entity.getAddress());
            emailField.setValue(entity.getEmail());
            uniEmailField.setValue(entity.getUniEmail());
            upload.setValue(entity.getPhoto());
        }

        layout.addComponents(usernameField, firstNameBox, lastNameBox, passwordField, userGroupBox, genderBox, birthdateField, personalNumberField, phoneNumberField, addressField, emailField, uniEmailField,
                upload);
    }

    private void initUpload() {
        upload = new UploadField() {

            Component lastImage = null;

            @Override
            protected void updateDisplay() {
                final byte[] pngData = (byte[]) getValue();

                String mimeType = getLastMimeType();
                if (mimeType.equals(UIUtils.IMAGE_FORMAT_PNG) || mimeType.equals(UIUtils.IMAGE_FORMAT_JPG) || mimeType.equals(UIUtils.IMAGE_FORMAT_JPEG)) {
                    Resource resource = new StreamResource((StreamResource.StreamSource) () -> new ByteArrayInputStream(pngData), "") {

                        @Override
                        public String getMIMEType() {
                            return UIUtils.IMAGE_FORMAT_PNG;
                        }
                    };

                    if (lastImage != null) {
                        getRootLayout().removeComponent(lastImage);
                    }

                    Embedded embedded = new Embedded("", resource);

                    embedded.setHeight(UIUtils.IMAGE_SIZE_IN_FORM, Unit.PIXELS);
                    embedded.setWidth(UIUtils.IMAGE_SIZE_IN_FORM, Unit.PIXELS);

                    lastImage = embedded;

                    getRootLayout().addComponent(embedded);
                } else {
                    super.updateDisplay();
                }
            }
        };

        upload.setFieldType(UploadField.FieldType.BYTE_ARRAY);
        upload.setAcceptFilter(UIUtils.IMAGE_FILTER);
        upload.setDisplayUpload(true);
        upload.setCaption(UIUtils.getTranslation(Translations.PHOTO));
    }

    @Override
    protected void save() {
        try {
            firstNameBox.validate();
            lastNameBox.validate();
            genderBox.validate();
            birthdateField.validate();

            UIUtils.validate(personalNumberField, phoneNumberField, addressField, emailField, uniEmailField);

            if (isAdd) {
                passwordField.validate();
            }
        } catch (InvalidValueException e) {
            firstNameBox.setValidationVisible(true);
            lastNameBox.setValidationVisible(true);
            genderBox.setValidationVisible(true);
            birthdateField.setValidationVisible(true);

            UIUtils.setValidationVisible(personalNumberField, phoneNumberField, addressField, emailField, uniEmailField);

            if (isAdd) {
                passwordField.setValidationVisible(true);
            }

            errorLabel.setValue(UIUtils.getTranslation(Translations.FILL_ALL_FIELDS));
            errorLabel.setVisible(true);

            return;
        }

        if (isAdd) {
            entity = new Lecturer();
        }

        entity.setFirstName(firstNameBox.getValue());
        entity.setLastName(lastNameBox.getValue());
        entity.setGender(genderBox.getValue());
        entity.setBirthDate(DateUtils.convertToLocalDate(birthdateField.getValue()));
        entity.setPersonalNumber(personalNumberField.getValue());
        entity.setPhoneNumber(phoneNumberField.getValue());
        entity.setAddress(addressField.getValue());
        entity.setEmail(emailField.getValue());
        entity.setUniEmail(uniEmailField.getValue());
        entity.setPhoto((byte[]) upload.getValue());

        User user;
        if (!isAdd) {
            user = entity.getUser();
        } else {
            user = new User();
        }

        user.setUserGroup(userGroupBox.getValue());
        user.setUsername(usernameField.getValue());
        user.setName(firstNameBox.getValue());

        if (!Utils.isBlank(passwordField.getValue())) {
            user.setPassword(SecurityUtils.encryptPassword(passwordField.getValue()));
        }

        entity.setUser(user);

        try {
            entity = isAdd ? manager.add(entity) : manager.update(entity);

            if (isAdd) {
                container.addItem(entity);
            } else {
                int index = container.indexOfId(entity);

                container.removeItem(entity);
                container.addItemAt(index, entity);
            }

            List<Lecturer> list = cachingService.getLecturers(null);

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