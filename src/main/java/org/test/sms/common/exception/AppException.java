package org.test.sms.common.exception;

import org.test.sms.common.enums.general.ErrorCode;

public class AppException extends Exception {

    private ErrorCode errorCode;

    private Object[] params;

    public AppException(ErrorCode errorCode, Object... params) {
        super(errorCode.toString());

        this.errorCode = errorCode;
        this.params = params;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public Object[] getParams() {
        return params;
    }
}