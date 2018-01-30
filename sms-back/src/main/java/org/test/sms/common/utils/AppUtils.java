package org.test.sms.common.utils;

import org.test.sms.common.entity.general.AbstractEntity;
import org.test.sms.common.enums.general.ErrorCodeType;
import org.test.sms.common.exception.AppException;

public class AppUtils {

    private AppUtils() {}

    public static void setEntityVersion(AbstractEntity abstractEntity, int newVersion) throws AppException {
        if (abstractEntity.getVersion() != newVersion) {
            throw new AppException(ErrorCodeType.OBJECT_CHANGED);
        }

        abstractEntity.setVersion(newVersion);
    }
}