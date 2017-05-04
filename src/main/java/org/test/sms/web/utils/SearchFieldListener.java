package org.test.sms.web.utils;

import com.vaadin.event.ShortcutListener;
import org.test.sms.common.utils.Action;

public class SearchFieldListener extends ShortcutListener {

    private Action action;

    public SearchFieldListener(Action action) {
        super(null, KeyCode.ENTER, null);
        this.action = action;
    }

    @Override
    public void handleAction(Object sender, Object target) {
        action.performAction();
    }
}