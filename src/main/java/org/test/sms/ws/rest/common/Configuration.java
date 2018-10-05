package org.test.sms.ws.rest.common;

import org.test.sms.ws.rest.common.enums.ProtocolType;

import java.util.List;

public interface Configuration {

    ProtocolType getProtocol();

    String getHost();

    int getPort();

    String getBaseUrl();

    List<Integer> getSuccessfulResponseCodes();
}