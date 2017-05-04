package org.test.sms.web.customComponents;

import com.vaadin.ui.Label;
import com.vaadin.ui.themes.ValoTheme;

public class CErrorLabel extends Label {

    public CErrorLabel() {
        addStyleName(ValoTheme.LABEL_FAILURE);
        setVisible(false);
    }
}