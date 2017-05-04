package org.test.sms.web.tabs;

import com.vaadin.ui.HorizontalLayout;
import org.test.sms.common.entities.UserGroup;
import org.test.sms.common.enums.TabType;
import org.test.sms.common.enums.Translations;
import org.test.sms.common.filters.UserGroupFilter;
import org.test.sms.common.service.common.UserGroupService;
import org.test.sms.common.utils.Utils;
import org.test.sms.web.customComponents.CSearchTextField;
import org.test.sms.web.forms.UserGroupForm;
import org.test.sms.web.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class UserGroupTab extends GenericTab<UserGroup> {

    private UserGroupService service;

    private UserGroupForm form;

    private CSearchTextField nameField;

    public UserGroupTab init() {
        return (UserGroupTab) super.init(TabType.USER_GROUP, UserGroup.class, service, form);
    }

    @Override
    protected void initSearchFilterContent(HorizontalLayout layout) {
        nameField = new CSearchTextField(this::reload);
        nameField.setInputPrompt(UIUtils.getTranslation(Translations.NAME));

        layout.addComponents(nameField);
    }

    @Override
    protected String getEntityColumns() {
        return "name";
    }

    @Override
    protected String getNestedEntityColumns() {
        return "";
    }

    @Override
    protected String getButtonColumns(Map<String, Consumer<UserGroup>> buttonActions) {
        return "";
    }

    @Override
    protected String getTextConverterColumns() {
        return "name";
    }

    @Override
    protected String getTranslationConverterColumns() {
        return "";
    }

    @Override
    protected String getColumnOrder() {
        return "name";
    }

    @Override
    protected void update(UserGroup entity) {
        form.init(tabType, service, service.get(entity.getId()).get(), container);
    }

    @Override
    protected void delete(UserGroup entity) {
        UIUtils.showConfirmDialog(entity.getName(), service, entity, container, cachingService.getUserGroups(null));
    }

    @Override
    protected void performAdditionalReloadActions() {
    }

    @Override
    protected List<UserGroup> getEntityList() {
        UserGroupFilter filter = new UserGroupFilter();

        List<String> names = null;

        String name = nameField.getValue();
        if (!Utils.isBlank(name)) {
            List<String> facultyNames = cachingService.getUserGroups(null).parallelStream().map(UserGroup::getName).collect(Collectors.toList());
            names = UIUtils.getTextKeys(facultyNames, name);
        }

        if (names != null && names.isEmpty()) {
            return new ArrayList<>();
        }

        filter.setNames(names);

        return cachingService.getUserGroups(filter);
    }
}