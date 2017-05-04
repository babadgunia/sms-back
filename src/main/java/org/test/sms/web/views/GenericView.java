package org.test.sms.web.views;

import com.kbdunn.vaadin.addons.fontawesome.FontAwesome;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import org.test.sms.common.entities.User;
import org.test.sms.common.enums.ActionType;
import org.test.sms.common.enums.LanguageType;
import org.test.sms.common.enums.Translations;
import org.test.sms.common.exception.AppException;
import org.test.sms.common.service.CachingService;
import org.test.sms.common.service.common.ActionLogService;
import org.test.sms.common.service.common.UserService;
import org.test.sms.common.utils.DateUtils;
import org.test.sms.web.utils.UIUtils;

import java.util.Objects;

import static org.test.sms.web.utils.UIUtils.showFailNotification;

public abstract class GenericView extends CustomComponent implements View {

    private CachingService cachingService;

    private ActionLogService actionLogService;

    private UserService userService;

    protected GenericView() {
    }

    protected abstract Component initHeader();

    protected abstract Component initContent();

    @Override
    public void enter(ViewChangeEvent event) {
        Component content = initContent();
        setSizeFull();

        VerticalLayout layout = new VerticalLayout(initHeader(), content, initFooter());
        layout.setSizeFull();
        layout.setMargin(new MarginInfo(false, true, false, true));
        layout.setComponentAlignment(content, Alignment.MIDDLE_CENTER);
        layout.setExpandRatio(content, 1);

        setCompositionRoot(layout);
    }

    protected HorizontalLayout initBasicHeader() {
        ComboBox languageSelect = initLanguageBox();

        HorizontalLayout layout = new HorizontalLayout(languageSelect);
        layout.setWidth(UIUtils.FULL_WIDTH);
        layout.setComponentAlignment(languageSelect, Alignment.BOTTOM_CENTER);

        return layout;
    }

    protected HorizontalLayout initFullHeader() {
        Label greetingLabel = initGreetingLabel();
        ComboBox languageBox = initLanguageBox();

        Button reloadCacheButton = new Button(UIUtils.getTranslation(Translations.RELOAD_CACHE), e -> cachingService.reload());
        reloadCacheButton.setIcon(FontAwesome.REFRESH);
        reloadCacheButton.addStyleName(ValoTheme.BUTTON_LINK);

        HorizontalLayout layout = new HorizontalLayout(greetingLabel, languageBox);

        layout.setWidth(UIUtils.FULL_WIDTH);
        layout.setComponentAlignment(greetingLabel, Alignment.MIDDLE_CENTER);
        layout.setExpandRatio(greetingLabel, 1);
        layout.setExpandRatio(languageBox, 1);

        if (UIUtils.isAdministrator()) {
            layout.addComponent(reloadCacheButton);
        }

        layout.addComponent(initLogoutButton());

        return layout;
    }

    private ComboBox initLanguageBox() {
        ComboBox languageBox = new ComboBox();
        for (LanguageType language : LanguageType.values()) {
            languageBox.addItem(language);

            String caption = UIUtils.getTranslation(Enum.valueOf(Translations.class, language.toString()));
            languageBox.setItemCaption(language, caption);
        }

        languageBox.setValue(UIUtils.getUserLanguage());
        languageBox.setNullSelectionAllowed(false);
        languageBox.setTextInputAllowed(false);

        if (Objects.isNull(UIUtils.getUser())) {
            languageBox.select(Translations.getLanguage());
        } else {
            languageBox.select(UIUtils.getUserLanguage());
        }

        languageBox.addStyleName(ValoTheme.COMBOBOX_BORDERLESS);
        languageBox.addValueChangeListener(e -> changeLanguage((LanguageType) e.getProperty().getValue()));

        return languageBox;
    }

    private Label initGreetingLabel() {
        String timeOfDayIcon = DateUtils.isDaylight() ? FontAwesome.SUN_O.getHtml() : FontAwesome.MOON_O.getHtml();
        User user = UIUtils.getUser();
        String greeting = timeOfDayIcon + " " + UIUtils.getTranslation(Translations.HELLO) + " " + user.getName();

        Label greetingLabel = new Label(greeting);
        greetingLabel.setContentMode(ContentMode.HTML);
        greetingLabel.addStyleName(ValoTheme.LABEL_COLORED);

        return greetingLabel;
    }

    private Button initLogoutButton() {
        Button logoutButton = new Button(UIUtils.getTranslation(Translations.LOGOUT), e -> {
            try {
                actionLogService.add(ActionType.LOGOUT, "logout", UIUtils.getUser().getUsername(), UIUtils.getIpAddress());
            } catch (AppException e1) {
            }
            getSession().setAttribute(User.class, null);
            UIUtils.navigateTo(LoginView.NAME);
        });

        logoutButton.setIcon(FontAwesome.SIGN_OUT);
        logoutButton.addStyleName(ValoTheme.BUTTON_LINK);

        return logoutButton;
    }

    private void changeLanguage(LanguageType language) {
        Translations.setLanguage(language);

        User user = UIUtils.getUser();
        if (Objects.nonNull(user)) {
            user = userService.get(user.getId()).get();
            user.setLanguage(language);

            try {
                userService.update(user);
            } catch (AppException e) {
                showFailNotification(UIUtils.getTranslation(e));
            }

            UI.getCurrent().getSession().setAttribute(User.class, user);
        }

        getUI().getPage().reload();
    }

    private HorizontalLayout initFooter() {
        String footerText = UIUtils.getTranslation(Translations.FOOTER) + " " + FontAwesome.COPYRIGHT.getHtml();
        Label footerLabel = new Label(footerText);
        footerLabel.setSizeUndefined();
        footerLabel.setContentMode(ContentMode.HTML);
        footerLabel.addStyleName(ValoTheme.LABEL_BOLD);
        footerLabel.addStyleName(ValoTheme.LABEL_TINY);

        HorizontalLayout layout = new HorizontalLayout(footerLabel);
        layout.setWidth(UIUtils.FULL_WIDTH);
        layout.setComponentAlignment(footerLabel, Alignment.TOP_CENTER);

        return layout;
    }
}