package org.test.sms.common.utils;

import javax.naming.InitialContext;
import javax.naming.NamingException;

public class AppUtils {

    private AppUtils() {
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(Class<T> clazz) {
        try {
            String local = "Local";
            String remote = "Remote";
            String className = clazz.getSimpleName();
            String beanName = null;

            if (className.endsWith(local)) {
                beanName = className.substring(0, className.lastIndexOf(local));
            } else if (className.endsWith(remote)) {
                beanName = className.substring(0, className.lastIndexOf(remote));
            }

            return (T) new InitialContext().lookup("java:global/sms/sms-server-1.0/" + beanName + "!" + clazz.getName());
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }
}