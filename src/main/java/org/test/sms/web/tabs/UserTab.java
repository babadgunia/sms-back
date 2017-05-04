package org.test.sms.web.tabs;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import org.test.sms.common.entities.general.User;
import org.test.sms.common.entities.general.UserGroup;
import org.test.sms.common.enums.TabType;
import org.test.sms.common.enums.Translations;
import org.test.sms.common.filters.general.UserFilter;
import org.test.sms.common.service.general.UserService;
import org.test.sms.common.utils.Utils;
import org.test.sms.web.customComponents.CSearchComboBox;
import org.test.sms.web.customComponents.CSearchTextField;
import org.test.sms.web.forms.UserForm;
import org.test.sms.web.utils.HTMLClasses;
import org.test.sms.web.utils.SearchFieldListener;
import org.test.sms.web.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class UserTab extends GenericTab<User> {

    private UserService service;

    private UserForm form;

    private CSearchTextField nameField;

    private CSearchTextField usernameField;

    private CSearchComboBox<UserGroup> userGroupBox;

    public UserTab init() {
        return (UserTab) super.init(TabType.USER, User.class, service, form);
    }

    @Override
    protected void initSearchFilterContent(HorizontalLayout layout) {
        nameField = new CSearchTextField(this::reload);
        nameField.setInputPrompt(UIUtils.getTranslation(Translations.NAME));

        usernameField = new CSearchTextField(this::reload);
        usernameField.setInputPrompt(UIUtils.getTranslation(Translations.USERNAME));

        Panel userGroupPanel = new Panel();

        userGroupPanel.setSizeUndefined();
        userGroupPanel.addShortcutListener(new SearchFieldListener(this::reload));
        userGroupPanel.addStyleName(HTMLClasses.NO_BORDER.getClassName());

        userGroupBox = new CSearchComboBox<>();
        userGroupBox.setInputPrompt(UIUtils.getTranslation(Translations.USERGROUP));

        userGroupPanel.setContent(userGroupBox);

        layout.addComponents(nameField, usernameField, userGroupPanel);
    }

    @Override
    protected String getEntityColumns() {
        return "name, username";
    }

    @Override
    protected String getNestedEntityColumns() {
        return "userGroup.name";
    }

    @Override
    protected String getButtonColumns(Map<String, Consumer<User>> buttonActions) {
        return "";
    }

    @Override
    protected String getTextConverterColumns() {
        return "name, userGroup.name";
    }

    @Override
    protected String getTranslationConverterColumns() {
        return "";
    }

    @Override
    protected String getColumnOrder() {
        return "name, username, userGroup.name";
    }

    @Override
    protected void update(User entity) {
        form.init(tabType, service, service.get(entity.getId()).get(), container);
    }

    @Override
    protected void delete(User entity) {
        UIUtils.showConfirmDialog(entity.getName(), service, entity, container, null);
    }

    @Override
    protected void performAdditionalReloadActions() {
        cachingService.getUserGroups(null).forEach(e -> {
            userGroupBox.addItem(e);
            userGroupBox.setItemCaption(e, UIUtils.getTextValue(e.getName()));
        });
    }

    @Override
    protected List<User> getEntityList() {
        UserFilter filter = new UserFilter();

        List<User> users = service.getList(null);

        List<String> names = null;

        String name = nameField.getValue();
        if (!Utils.isBlank(name)) {
            List<String> userNames = users.parallelStream().map(User::getName).collect(Collectors.toList());
            names = UIUtils.getTextKeys(userNames, name);
        }

        if (names != null && names.isEmpty()) {
            return new ArrayList<>();
        }

        filter.setNames(names);

        String username = usernameField.getValue();
        if (Utils.isBlank(username)) {
            username = null;
        }

        filter.setUsername(username);
        filter.setUserGroup(userGroupBox.getValue());

        return service.getList(filter);
    }
}