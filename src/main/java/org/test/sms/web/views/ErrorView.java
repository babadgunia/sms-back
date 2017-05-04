package org.test.sms.web.views;

import com.kbdunn.vaadin.addons.fontawesome.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import org.test.sms.common.enums.Translations;
import org.test.sms.web.utils.HTMLClasses;
import org.test.sms.web.utils.UIUtils;

public class ErrorView extends GenericView {

    public static final String NAME = "Error";

    @Override
    protected HorizontalLayout initHeader() {
        return initBasicHeader();
    }

    @Override
    protected Component initContent() {
        Label errorImageLabel = FontAwesome.GEARS.getLabel().setSize6x();
        errorImageLabel.setSizeUndefined();
        errorImageLabel.addStyleName(HTMLClasses.ERROR_IMAGE.getClassName());

        Label errorTextLabel = new Label(UIUtils.getTranslation(Translations.ERROR_VIEW));
        errorTextLabel.addStyleName(ValoTheme.LABEL_BOLD);
        errorTextLabel.setSizeUndefined();

        Button backButton = new Button(UIUtils.getTranslation(Translations.BACK), e -> UIUtils.navigateTo(LoginView.NAME));

        backButton.setIcon(FontAwesome.BACKWARD);
        backButton.addStyleName(ValoTheme.BUTTON_LINK);

        VerticalLayout layout = new VerticalLayout(errorImageLabel, errorTextLabel, backButton);
        layout.setSizeUndefined();
        layout.setSpacing(true);
        layout.setComponentAlignment(errorImageLabel, Alignment.MIDDLE_CENTER);
        layout.setComponentAlignment(backButton, Alignment.MIDDLE_CENTER);

        return layout;
    }
}