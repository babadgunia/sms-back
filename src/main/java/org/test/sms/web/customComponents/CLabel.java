package org.test.sms.web.customComponents;

import com.vaadin.ui.Label;
import com.vaadin.ui.themes.ValoTheme;

public class CLabel extends Label {

    public CLabel() {
        this("");
    }

    public CLabel(String caption) {
        super(caption);

        addStyleName(ValoTheme.LABEL_BOLD);
    }
}