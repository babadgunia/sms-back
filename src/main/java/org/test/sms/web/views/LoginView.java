package org.test.sms.web.views;

import com.vaadin.ui.Component;
import org.test.sms.web.customComponents.CLoginForm;

public class LoginView extends GenericView {

    public static final String NAME = "Login";

    private CLoginForm loginForm;

    @Override
    protected Component initHeader() {
        return initBasicHeader();
    }

    @Override
    protected Component initContent() {
        return loginForm;
    }
}