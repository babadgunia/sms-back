package org.test.sms.web.customComponents;

import com.vaadin.data.validator.NullValidator;
import com.vaadin.ui.ComboBox;
import org.test.sms.common.enums.Translations;
import org.test.sms.web.utils.HTMLClasses;
import org.test.sms.web.utils.UIUtils;

public class CComboBox<T> extends ComboBox {

    public CComboBox(String caption) {
        super(caption);

        setNullSelectionAllowed(false);

        addValidator(new NullValidator(UIUtils.getTranslation(Translations.FIELD_CANNOT_BE_EMPTY), false));
        setValidationVisible(false);

        addStyleName(HTMLClasses.FULL_WIDTH.getClassName());
    }

    @Override
    @SuppressWarnings("unchecked")
    public T getValue() {
        return (T) super.getValue();
    }
}