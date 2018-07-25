package org.test.sms.ws.rest.common.enums;

public enum ProtocolType {

    HTTP,

    HTTPS;

    public String getName() {
        return toString().toLowerCase();
    }
}