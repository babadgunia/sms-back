package org.test.sms.web.customComponents;

import com.vaadin.ui.JavaScript;
import com.vaadin.ui.TextField;
import org.test.sms.web.utils.HTMLClasses;
import org.test.sms.web.utils.JSFunctions;

public class CNumberField extends TextField {

    public CNumberField() {
        addStyleName(HTMLClasses.NUMBER_INPUT.getClassName());
        JavaScript.getCurrent().execute(JSFunctions.NUMBER_INPUT.getJSFunction(HTMLClasses.NUMBER_INPUT.getClassName()));
    }
}
