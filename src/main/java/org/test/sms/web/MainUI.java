package org.test.sms.web;

import com.vaadin.annotations.JavaScript;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.navigator.ViewProvider;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;
import org.test.sms.common.enums.SystemProperties;
import org.test.sms.web.customComponents.CErrorHandler;
import org.test.sms.web.utils.UIUtils;
import org.test.sms.web.views.ErrorView;
import org.test.sms.web.views.HomepageView;
import org.test.sms.web.views.LoginView;

import javax.servlet.annotation.WebServlet;
import java.util.Objects;

@Theme("app")
@Widgetset("org.test.sms.AppWidgetset")
@JavaScript({"vaadin://js/app.js"})
public class MainUI extends UI {

    private CErrorHandler errorHandler;

    private ViewProvider viewProvider;

    @Override
    protected void init(VaadinRequest request) {
        setErrorHandler(errorHandler);

        Navigator navigator = new Navigator(this, this);
        navigator.addProvider(viewProvider);
        navigator.setErrorView(viewProvider.getView(ErrorView.NAME));

        navigator.addViewChangeListener(new ViewChangeListener() {

            @Override
            public boolean beforeViewChange(ViewChangeEvent event) {
                return validateViewChange(navigator, event);
            }

            @Override
            public void afterViewChange(ViewChangeEvent event) {
            }
        });

        navigator.navigateTo(LoginView.NAME);
    }

    private boolean validateViewChange(Navigator navigator, ViewChangeEvent event) {
        boolean isLoggedIn = Objects.nonNull(UIUtils.getUser());

        View newView = event.getNewView();
        boolean isNavigatingToLoginView = newView instanceof LoginView;
        boolean isNavigatingToErrorView = newView instanceof ErrorView;

        if (!isLoggedIn && !(isNavigatingToLoginView || isNavigatingToErrorView)) {
            navigator.navigateTo(LoginView.NAME);

            return false;
        }

        if (isLoggedIn && isNavigatingToLoginView) {
            navigator.navigateTo(HomepageView.NAME);

            return false;
        }

        getPage().setTitle(SystemProperties.APP_NAME.getValue() + " > " + event.getViewName());

        return true;
    }

    @WebServlet(urlPatterns = "/*", asyncSupported = true)
    @VaadinServletConfiguration(ui = MainUI.class, productionMode = true)
    public static class MainUIServlet extends VaadinServlet {
    }
}