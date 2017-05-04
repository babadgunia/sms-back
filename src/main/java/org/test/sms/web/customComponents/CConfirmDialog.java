package org.test.sms.web.customComponents;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;
import org.test.sms.common.utils.Action;
import org.test.sms.web.utils.UIUtils;

import java.util.function.BooleanSupplier;

public class CConfirmDialog extends Window {

    private String confirmText;

    private String confirmButtonCaption;

    private String cancelButtonCaption;

    private BooleanSupplier confirmActionListener;

    private Action[] additionalActions;

    public CConfirmDialog(String caption, String confirmText, String confirmButtonCaption, String cancelButtonCaption, BooleanSupplier confirmActionListener, Action... additionalActions) {
        this.confirmText = confirmText;
        this.confirmButtonCaption = confirmButtonCaption;
        this.cancelButtonCaption = cancelButtonCaption;
        this.confirmActionListener = confirmActionListener;
        this.additionalActions = additionalActions;

        setModal(true);
        setCaption(caption);
        setContent(initContent());
    }

    private Component initContent() {
        VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);
        layout.setSpacing(true);

        CLabel confirmLabel = new CLabel(confirmText);

        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setSpacing(true);

        Button confirmButton = new Button(confirmButtonCaption, e -> {
            if (confirmActionListener.getAsBoolean()) {
                for (Action additionalAction : additionalActions) {
                    additionalAction.performAction();
                }

                UIUtils.showSuccessNotification();

                close();
            }
        });
        confirmButton.addStyleName(ValoTheme.BUTTON_PRIMARY);

        Button cancelButton = new Button(cancelButtonCaption, e -> close());

        buttonLayout.addComponents(confirmButton, cancelButton);

        layout.addComponents(confirmLabel, buttonLayout);
        layout.setComponentAlignment(buttonLayout, Alignment.MIDDLE_RIGHT);

        return layout;
    }

    public static void show(String caption, String confirmText, String confirmButtonCaption, String cancelButtonCaption, BooleanSupplier confirmActionListener, Action... additionalActions) {
        UI.getCurrent().addWindow(new CConfirmDialog(caption, confirmText, confirmButtonCaption, cancelButtonCaption, confirmActionListener, additionalActions));
    }
}