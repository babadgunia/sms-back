package org.test.sms.web.utils;

import com.vaadin.data.validator.AbstractStringValidator;
import org.test.sms.common.enums.Translations;
import org.test.sms.common.utils.Utils;

public class StringFieldValidator extends AbstractStringValidator {

    public StringFieldValidator() {
        super(UIUtils.getTranslation(Translations.FIELD_CANNOT_BE_EMPTY));
    }

    @Override
    protected boolean isValidValue(String value) {
        return !Utils.isBlank(value);
    }
}