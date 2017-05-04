package org.test.sms.web.customComponents;

import com.vaadin.data.validator.NullValidator;
import com.vaadin.ui.PopupDateField;
import org.test.sms.common.enums.Translations;
import org.test.sms.web.utils.HTMLClasses;
import org.test.sms.web.utils.UIUtils;

public class CPopupDateField extends PopupDateField {

    public CPopupDateField(String caption) {
        super(caption);

        addValidator(new NullValidator(UIUtils.getTranslation(Translations.FIELD_CANNOT_BE_EMPTY), false));
        setValidationVisible(false);

        addStyleName(HTMLClasses.FULL_WIDTH.getClassName());
    }
}