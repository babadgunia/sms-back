package org.test.sms.web.forms;

import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.FormLayout;
import org.test.sms.common.entities.I18NText;
import org.test.sms.common.entities.Text;
import org.test.sms.common.enums.LanguageType;
import org.test.sms.common.enums.Translations;
import org.test.sms.common.exception.AppException;
import org.test.sms.web.customComponents.CTextField;
import org.test.sms.web.utils.UIUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TextForm extends GenericForm<Text> {

    private CTextField keyField;

    private Map<LanguageType, CTextField> valueFields = new LinkedHashMap<>();

    @Override
    protected void initFormContent(FormLayout layout) {
        keyField = new CTextField(UIUtils.getTranslation(Translations.KEY));

        Arrays.stream(LanguageType.values()).forEach(e -> valueFields.put(e, new CTextField(UIUtils.getTranslation(e))));

        if (!isAdd) {
            keyField.setValue(entity.getKey());
            keyField.setEnabled(false);
            entity.getValues().parallelStream().forEach(e -> valueFields.get(e.getLanguage()).setValue(e.getValue()));
        }

        layout.addComponents(keyField);
        valueFields.values().forEach(layout::addComponent);
    }

    @Override
    protected void save() {
        try {
            keyField.validate();
            valueFields.values().parallelStream().forEach(AbstractField::validate);
        } catch (InvalidValueException e) {
            keyField.setValidationVisible(true);
            valueFields.values().parallelStream().forEach(t -> t.setValidationVisible(true));

            errorLabel.setValue(UIUtils.getTranslation(Translations.FILL_ALL_FIELDS));
            errorLabel.setVisible(true);

            return;
        }

        if (isAdd) {
            entity = new Text();
        }

        entity.setKey(keyField.getValue().toUpperCase());

        List<I18NText> values = new ArrayList<>();
        valueFields.keySet().forEach(e -> {
            I18NText i18ntext = new I18NText();

            i18ntext.setLanguage(e);
            i18ntext.setValue(valueFields.get(e).getValue());
            i18ntext.setText(entity);

            values.add(i18ntext);
        });
        entity.setValues(values);

        try {
            entity = isAdd ? manager.add(entity) : manager.update(entity);

            if (isAdd) {
                container.addItem(entity);
            } else {
                int index = container.indexOfId(entity);
                container.removeItem(entity);
                container.addItemAt(index, entity);
            }

            List<Text> texts = cachingService.getTexts(null);
            if (isAdd) {
                texts.add(entity);
            } else {
                texts.set(texts.indexOf(entity), entity);
            }

            UIUtils.showSuccessNotification();

            close();
        } catch (AppException e) {
            errorLabel.setValue(UIUtils.getTranslation(e));
            errorLabel.setVisible(true);
        }
    }
}