package org.test.sms.web.forms;

import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.ui.FormLayout;
import org.test.sms.common.entities.general.Tab;
import org.test.sms.common.entities.general.UserGroup;
import org.test.sms.common.enums.TabPermissionType;
import org.test.sms.common.enums.TabType;
import org.test.sms.common.enums.Translations;
import org.test.sms.common.exception.AppException;
import org.test.sms.web.customComponents.CTree;
import org.test.sms.web.customComponents.CTreeItem;
import org.test.sms.web.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class UserGroupForm extends GenericForm<UserGroup> {

    private CTree tabTree;

    @Override
    protected void initFormContent(FormLayout layout) {
        initNameBox(layout, (Objects.nonNull(entity) ? entity.getName() : null));

        tabTree = new CTree(UIUtils.getTranslation(Translations.TABS));
        List<CTreeItem<TabType>> selectedTabs = new ArrayList<>();

        if (!isAdd) {
            List<Tab> tabs = UIUtils.getUserTabs();
            for (Tab tab : tabs) {
                CTreeItem<TabType> parent = new CTreeItem<>(tab.getType());
                tabTree.addParent(parent);
                tabTree.select(parent);

                selectedTabs.add(parent);
            }
        }

        for (TabType tabType : TabType.values()) {
            CTreeItem<TabType> parent;

            Optional<CTreeItem<TabType>> parentWrapper = selectedTabs.stream().filter(e -> e.getValue() == tabType).findAny();
            if (parentWrapper.isPresent()) {
                parent = parentWrapper.get();
            } else {
                parent = new CTreeItem<>(tabType);
                tabTree.addParent(parent);
            }

            for (TabPermissionType tabPermissionType : tabType.getPermissions()) {
                CTreeItem<TabPermissionType> child = new CTreeItem<>(tabPermissionType);
                tabTree.addChild(child, parent);

                if (!isAdd && entity.getTabs().parallelStream().anyMatch(e -> tabType == e.getType() && e.getPermissions().contains(tabPermissionType))) {
                    tabTree.select(child);
                }

            }
        }

        layout.addComponents(tabTree);
    }

    @Override
    protected void save() {
        try {
            nameBox.validate();
        } catch (InvalidValueException e) {
            nameBox.setValidationVisible(true);

            errorLabel.setValue(UIUtils.getTranslation(Translations.FILL_ALL_FIELDS));
            errorLabel.setVisible(true);

            return;
        }

        if (isAdd) {
            entity = new UserGroup();
        }

        entity.setName(nameBox.getValue());
        entity.setTabs(getTabsList());

        try {
            entity = isAdd ? manager.add(entity) : manager.update(entity);

            if (isAdd) {
                container.addItem(entity);
            } else {
                int index = container.indexOfId(entity);
                container.removeItem(entity);
                container.addItemAt(index, entity);
            }

            List<UserGroup> list = cachingService.getUserGroups(null);
            if (isAdd) {
                list.add(entity);
            } else {
                list.set(list.indexOf(entity), entity);
            }

            UIUtils.showSuccessNotification();

            close();
        } catch (AppException e) {
            errorLabel.setValue(UIUtils.getTranslation(e));
            errorLabel.setVisible(true);
        }
    }

    private List<Tab> getTabsList() {
        List<Tab> tabs = new ArrayList<>();
        for (int i = 0; i < tabTree.getParents().size(); i++) {
            CTreeItem<?> parent = tabTree.getParents().get(i);
            if (parent.isSelected()) {
                Optional<Tab> tabWrapper = entity.getTabs().parallelStream().filter(e -> e.getType() == parent.getValue()).findAny();
                Tab tab = tabWrapper.orElseGet(Tab::new);
                tab.setType((TabType) parent.getValue());
                List<TabPermissionType> permissions = new ArrayList<>();
                for (Object childItem : tabTree.getChildren(parent)) {
                    CTreeItem<?> child = (CTreeItem<?>) childItem;
                    if (child.isSelected()) {
                        permissions.add((TabPermissionType) child.getValue());
                    }
                }
                tab.setPermissions(permissions);
                tab.setUserGroup(entity);
                tab.setPosition(i);
                tabs.add(tab);
            }
        }

        return tabs;
    }
}