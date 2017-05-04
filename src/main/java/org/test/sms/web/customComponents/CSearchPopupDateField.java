package org.test.sms.web.customComponents;

import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.PopupDateField;
import org.test.sms.web.utils.HTMLClasses;

public class CSearchPopupDateField extends PopupDateField {

    public CSearchPopupDateField(String inputPrompt) {
        setInputPrompt(inputPrompt);
        setResolution(Resolution.MINUTE);
        addStyleName(HTMLClasses.FULL_WIDTH.getClassName());
    }
}