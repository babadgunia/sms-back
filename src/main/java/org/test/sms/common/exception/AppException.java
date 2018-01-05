package org.test.sms.common.exception;

import lombok.Getter;
import org.test.sms.common.enums.general.ErrorCodeType;

@Getter
public class AppException extends Exception {

    private ErrorCodeType errorCode;

    private Object[] params;

    public AppException(ErrorCodeType errorCode, Object... params) {
        super(errorCode.toString());

        this.errorCode = errorCode;
        this.params = params;
    }
}