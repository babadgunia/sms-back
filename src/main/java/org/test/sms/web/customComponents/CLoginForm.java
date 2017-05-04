package org.test.sms.web.customComponents;

import com.kbdunn.vaadin.addons.fontawesome.FontAwesome;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.LoginForm;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import org.test.sms.common.entities.User;
import org.test.sms.common.enums.ActionType;
import org.test.sms.common.enums.Translations;
import org.test.sms.common.exception.AppException;
import org.test.sms.common.service.common.ActionLogService;
import org.test.sms.common.service.common.UserService;
import org.test.sms.common.utils.SecurityUtils;
import org.test.sms.web.utils.StringFieldValidator;
import org.test.sms.web.utils.UIUtils;
import org.test.sms.web.views.HomepageView;

import java.util.Optional;

public class CLoginForm extends LoginForm {

    private ActionLogService actionLogService;

    private UserService userService;

    private TextField usernameField;

    private PasswordField passwordField;

    private Label errorLabel;

    @Override
    protected Component createContent(TextField userNameField, PasswordField passwordField, Button loginButton) {
        VerticalLayout content = new VerticalLayout(userNameField, passwordField, loginButton, initErrorLabel());
        content.setSpacing(true);

        addLoginListener(e -> login());

        return content;
    }

    @Override
    protected TextField createUsernameField() {
        usernameField = new TextField(UIUtils.getTranslation(Translations.USERNAME));
        usernameField.setIcon(FontAwesome.USER);
        usernameField.setSizeFull();
        usernameField.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
        usernameField.addStyleName(ValoTheme.TEXTFIELD_LARGE);
        usernameField.addValidator(new StringFieldValidator());
        usernameField.setValidationVisible(false);

        return usernameField;
    }

    @Override
    protected PasswordField createPasswordField() {
        passwordField = new PasswordField(UIUtils.getTranslation(Translations.PASSWORD));
        passwordField.setIcon(FontAwesome.LOCK);
        passwordField.setSizeFull();
        passwordField.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
        passwordField.addStyleName(ValoTheme.TEXTFIELD_LARGE);
        passwordField.addValidator(new StringFieldValidator());
        passwordField.setValidationVisible(false);

        return passwordField;
    }

    @Override
    protected Button createLoginButton() {
        Button loginButton = new Button(UIUtils.getTranslation(Translations.LOGIN));
        loginButton.setIcon(FontAwesome.SIGN_IN);
        loginButton.setSizeFull();
        loginButton.addStyleName(ValoTheme.BUTTON_ICON_ALIGN_RIGHT);
        loginButton.addStyleName(ValoTheme.BUTTON_PRIMARY);

        return loginButton;
    }

    private Label initErrorLabel() {
        errorLabel = new Label();
        errorLabel.addStyleName(ValoTheme.LABEL_FAILURE);
        errorLabel.setVisible(false);

        return errorLabel;
    }

    private void login() {
        String username = usernameField.getValue();
        String ipAddress = UIUtils.getIpAddress();

        if (!isValidInput(username, passwordField.getValue())) {
            try {
                actionLogService.add(ActionType.LOGIN_ATTEMPT, "login attempt with username " + username, null, ipAddress);
            } catch (AppException e) {
            }

            return;
        }

        errorLabel.setVisible(false);

        try {
            actionLogService.add(ActionType.LOGIN, "login", username, ipAddress);
        } catch (AppException e) {
        }

        UIUtils.navigateTo(HomepageView.NAME);
    }

    private boolean isValidInput(String username, String password) {
        try {
            usernameField.validate();
            passwordField.validate();
        } catch (InvalidValueException e) {
            errorLabel.setValue(UIUtils.getTranslation(Translations.FILL_ALL_FIELDS));
            errorLabel.setVisible(true);
            usernameField.setValidationVisible(true);
            passwordField.setValidationVisible(true);

            return false;
        }

        Optional<User> userWrapper = userService.get(username);
        if (!userWrapper.isPresent()) {
            errorLabel.setValue(UIUtils.getTranslation(Translations.USERNAME_NOT_FOUND));
            errorLabel.setVisible(true);

            return false;
        }

        User user = userWrapper.get();
        String storedPassword = user.getPassword();
        if (!SecurityUtils.validatePassword(password, storedPassword)) {
            errorLabel.setValue(UIUtils.getTranslation(Translations.WRONG_PASSWORD));
            errorLabel.setVisible(true);

            return false;
        }

        user.setPassword(null);
        getSession().setAttribute(User.class, user);

        return true;
    }
}