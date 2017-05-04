package org.test.sms.web.converter;

import com.vaadin.data.util.converter.Converter;
import org.test.sms.common.enums.university.ExamType;
import org.test.sms.web.utils.UIUtils;

import java.util.Locale;

public class TranslationConverter implements Converter<String, ExamType> {

    @Override
    public ExamType convertToModel(String value, Class<? extends ExamType> targetType, Locale locale) throws ConversionException {
        return null;
    }

    @Override
    public String convertToPresentation(ExamType value, Class<? extends String> targetType, Locale locale) throws ConversionException {
        return UIUtils.getTranslation(value);
    }

    @Override
    public Class<ExamType> getModelType() {
        return ExamType.class;
    }

    @Override
    public Class<String> getPresentationType() {
        return String.class;
    }
}