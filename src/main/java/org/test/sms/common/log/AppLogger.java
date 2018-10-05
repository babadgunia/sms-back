package org.test.sms.common.log;

import org.jboss.logging.Logger;

public class AppLogger {

    private Logger logger;

    private AppLogger(Logger logger) {
        this.logger = logger;
    }

    public static AppLogger getLogger(Class<?> clazz) {
        return new AppLogger(Logger.getLogger(clazz));
    }

    public void info(Object info) {
        logger.info(info);
    }

    public void warn(Object warn) {
        logger.warn(warn);
    }

    public void error(Throwable t) {
        logger.error("", t);
    }

    public void error(Object error, Throwable t) {
        logger.error(error, t);
    }
}