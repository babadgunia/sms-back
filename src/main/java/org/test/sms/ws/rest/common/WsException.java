package org.test.sms.ws.rest.common;

public class WsException extends Exception {

    WsException(String message) {
        super(message);
    }

    WsException(Throwable cause) {
        super(cause);
    }
}