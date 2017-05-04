package org.test.sms.web.customComponents;

import com.vaadin.ui.JavaScript;
import com.vaadin.ui.PasswordField;
import org.test.sms.web.utils.HTMLClasses;
import org.test.sms.web.utils.JSFunctions;
import org.test.sms.web.utils.StringFieldValidator;

public class CPasswordField extends PasswordField {

    public CPasswordField(String caption) {
        super(caption);

        addValidator(new StringFieldValidator());
        setValidationVisible(false);

        String autocompleteOffClassName = HTMLClasses.AUTOCOMPLETE_OFF.getClassName();

        addStyleName(HTMLClasses.FULL_WIDTH.getClassName());
        addStyleName(autocompleteOffClassName);
        JavaScript.getCurrent().execute(JSFunctions.AUTOCOMPLETE_OFF.getJSFunction(autocompleteOffClassName));
    }
}