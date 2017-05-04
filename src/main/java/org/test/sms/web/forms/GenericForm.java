package org.test.sms.web.forms;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;
import org.test.sms.common.entities.AppEntity;
import org.test.sms.common.enums.TabPermissionType;
import org.test.sms.common.enums.TabType;
import org.test.sms.common.enums.Translations;
import org.test.sms.common.service.AbstractService;
import org.test.sms.common.service.CachingService;
import org.test.sms.web.customComponents.CComboBox;
import org.test.sms.web.customComponents.CErrorLabel;
import org.test.sms.web.utils.HTMLClasses;
import org.test.sms.web.utils.UIUtils;

import java.util.Objects;

public abstract class GenericForm<T extends AppEntity> extends Window {

    protected TabType tabType;

    protected T entity;

    protected BeanItemContainer<T> container;

    protected AbstractService<T> manager;

    protected CachingService cachingService;

    protected boolean isAdd;

    protected Label errorLabel;

    protected CComboBox<String> nameBox;

    protected Button saveButton;

    protected Button cancelButton;

    protected HorizontalLayout buttonLayout;

    protected abstract void initFormContent(FormLayout layout);

    protected abstract void save();

    public void init(TabType tabType, AbstractService<T> manager, T entity, BeanItemContainer<T> container) {
        this.tabType = tabType;
        this.manager = manager;
        this.entity = entity;
        this.container = container;

        isAdd = Objects.isNull(this.entity);

        setModal(true);
        setCaption(isAdd ? UIUtils.getTranslation(Translations.ADD) : UIUtils.getTranslation(Translations.EDIT));
        setContent(initContent());

        UI.getCurrent().addWindow(this);
    }

    private FormLayout initContent() {
        FormLayout layout = new FormLayout();

        layout.setMargin(true);
        layout.setWidthUndefined();

        buttonLayout = new HorizontalLayout();
        buttonLayout.addStyleName(HTMLClasses.RIGHT_ALIGNMENT.getClassName());
        buttonLayout.setSpacing(true);

        errorLabel = new CErrorLabel();

        saveButton = new Button(UIUtils.getTranslation(Translations.SAVE), e -> save());
        saveButton.addStyleName(ValoTheme.BUTTON_PRIMARY);

        cancelButton = new Button(UIUtils.getTranslation(Translations.CANCEL), e -> close());

        if (UIUtils.hasPermission(tabType, TabPermissionType.EDIT)) {
            buttonLayout.addComponent(saveButton);
        }
        buttonLayout.addComponent(cancelButton);

        initFormContent(layout);

        layout.addComponents(errorLabel, buttonLayout);

        return layout;
    }

    protected void initNameBox(FormLayout layout, String name) {
        nameBox = new CComboBox<>(UIUtils.getTranslation(Translations.NAME));

        UIUtils.getTextKeys().forEach(e -> {
            nameBox.addItem(e);
            nameBox.setItemCaption(e, UIUtils.getTextValue(e));
        });

        if (!isAdd) {
            nameBox.setValue(name);
        }

        layout.addComponent(nameBox);
    }
}