package org.test.sms.web.customComponents;

import com.vaadin.ui.TextField;
import org.test.sms.common.utils.Action;
import org.test.sms.web.utils.HTMLClasses;
import org.test.sms.web.utils.SearchFieldListener;

public class CSearchTextField extends TextField {

    public CSearchTextField() {
    }

    public CSearchTextField(Action action) {
        SearchFieldListener listener = new SearchFieldListener(action);

        addFocusListener(e -> addShortcutListener(listener));
        addBlurListener(e -> removeShortcutListener(listener));

        addStyleName(HTMLClasses.FULL_WIDTH.getClassName());
    }

    @Override
    public String getValue() {
        return super.getValue().trim();
    }
}