package org.test.sms.web.customComponents;

import com.vaadin.shared.Position;
import com.vaadin.ui.Notification;

public class CNotification extends Notification {

    public CNotification(String caption, Position position, String styleName) {
        super(caption);

        setPosition(position);
        setStyleName(styleName);
    }

    public CNotification(String caption, Position position, int delayMsec, String styleName) {
        super(caption);

        setPosition(position);
        setDelayMsec(delayMsec);
        setStyleName(styleName);
    }
}