package org.test.sms.web.converter;

import com.vaadin.data.util.converter.Converter;
import org.test.sms.web.utils.UIUtils;

import java.util.Locale;

public class TextConverter implements Converter<String, String> {

    @Override
    public String convertToModel(String value, Class<? extends String> targetType, Locale locale) throws ConversionException {
        return null;
    }

    @Override
    public String convertToPresentation(String value, Class<? extends String> targetType, Locale locale) throws ConversionException {
        return UIUtils.getTextValue(value);
    }

    @Override
    public Class<String> getModelType() {
        return String.class;
    }

    @Override
    public Class<String> getPresentationType() {
        return String.class;
    }
}