package org.test.sms.web.customComponents;

import com.vaadin.ui.TextField;
import org.test.sms.web.utils.HTMLClasses;
import org.test.sms.web.utils.StringFieldValidator;

public class CTextField extends TextField {

    public CTextField(String text) {
        super(text);

        addValidator(new StringFieldValidator());
        setValidationVisible(false);

        addStyleName(HTMLClasses.FULL_WIDTH.getClassName());
    }

    @Override
    public String getValue() {
        return super.getValue().trim();
    }
}