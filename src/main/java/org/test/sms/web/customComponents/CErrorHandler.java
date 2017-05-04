package org.test.sms.web.customComponents;

import com.vaadin.server.DefaultErrorHandler;
import com.vaadin.server.ErrorEvent;
import org.test.sms.common.enums.Translations;
import org.test.sms.common.exception.AppException;
import org.test.sms.common.log.AppLogger;
import org.test.sms.common.service.common.ExceptionLogService;
import org.test.sms.web.utils.UIUtils;

public class CErrorHandler extends DefaultErrorHandler {

    private AppLogger logger;

    private ExceptionLogService exceptionLogService;

    @Override
    public void error(ErrorEvent event) {
        Throwable throwable = event.getThrowable();
        Throwable t = findRelevantThrowable(throwable);

        try {
            exceptionLogService.add(t);
        } catch (AppException e) {
        }
        logger.error(throwable);
        UIUtils.showFailNotification(UIUtils.getTranslation(Translations.SYSTEM_ERROR));
    }
}