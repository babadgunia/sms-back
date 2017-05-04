package org.test.sms.web.customComponents;

import com.vaadin.ui.Component;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.themes.ValoTheme;
import org.test.sms.web.utils.Reloadable;
import org.test.sms.web.utils.UIUtils;

import java.util.Objects;

public class CTabSheet extends TabSheet {

    public CTabSheet() {
        setSizeFull();

        addStyleName(ValoTheme.TABSHEET_COMPACT_TABBAR);
        addStyleName(ValoTheme.TABSHEET_EQUAL_WIDTH_TABS);
        addStyleName(ValoTheme.TABSHEET_FRAMED);
    }

    public CTabSheet addTabChangeListener(TabSheet.Tab redirectorTab, String newTabViewName) {
        addSelectedTabChangeListener(e -> {
            Component tab = getSelectedTab();
            if (Objects.nonNull(redirectorTab) && tab.equals(redirectorTab.getComponent())) {
                UIUtils.navigateTo(newTabViewName);
            }

            if (tab instanceof Reloadable) {
                ((Reloadable) tab).reload();
            }
        });

        return this;
    }

    public CTabSheet initTab(int index) {
        TabSheet.Tab tab = getTab(index);
        if (Objects.nonNull(tab)) {
            setSelectedTab(tab);

            Component tabComponent = tab.getComponent();
            if (tabComponent instanceof Reloadable) {
                ((Reloadable) tabComponent).reload();
            }
        }

        return this;
    }
}