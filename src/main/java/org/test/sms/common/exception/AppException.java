package org.test.sms.common.exception;

import lombok.Getter;
import org.test.sms.common.enums.general.ErrorCode;

@Getter
public class AppException extends Exception {

    private ErrorCode errorCode;

    private Object[] params;

    public AppException(ErrorCode errorCode, Object... params) {
        super(errorCode.toString());

        this.errorCode = errorCode;
        this.params = params;
    }
}