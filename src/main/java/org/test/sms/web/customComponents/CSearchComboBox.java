package org.test.sms.web.customComponents;

import com.vaadin.ui.ComboBox;
import org.test.sms.web.utils.HTMLClasses;

public class CSearchComboBox<T> extends ComboBox {

    public CSearchComboBox() {
        addStyleName(HTMLClasses.FULL_WIDTH.getClassName());
    }

    @Override
    @SuppressWarnings("unchecked")
    public T getValue() {
        return (T) super.getValue();
    }
}