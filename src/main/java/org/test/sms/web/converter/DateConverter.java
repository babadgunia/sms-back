package org.test.sms.web.converter;

import com.vaadin.data.util.converter.Converter;
import org.test.sms.common.utils.DateUtils;
import org.test.sms.web.utils.UIUtils;

import java.time.LocalDateTime;
import java.util.Locale;

public class DateConverter implements Converter<String, LocalDateTime> {

    @Override
    public LocalDateTime convertToModel(String value, Class<? extends LocalDateTime> targetType, Locale locale) throws ConversionException {
        return null;
    }

    @Override
    public String convertToPresentation(LocalDateTime value, Class<? extends String> targetType, Locale locale) throws ConversionException {
        return UIUtils.SHORT_YEAR_NO_SECONDS_DATE_FORMAT.format(DateUtils.converToDate(value));
    }

    @Override
    public Class<LocalDateTime> getModelType() {
        return LocalDateTime.class;
    }

    @Override
    public Class<String> getPresentationType() {
        return String.class;
    }
}