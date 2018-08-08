package org.test.sms.ws.rest.common;

import org.test.sms.ws.rest.common.enums.HttpMethodType;

import java.util.HashMap;
import java.util.Map;

public abstract class Service {

    protected abstract Configuration getConfiguration();

    protected abstract String getUrl();

    protected <T> T sendRequest(String url, HttpMethodType httpMethod, Class<T> responseClass) throws WsException {
        return sendRequest(url, httpMethod, new HashMap<>(), null, responseClass);
    }

    protected <T> T sendRequest(String url, HttpMethodType httpMethod, Map<String, String> headers, Object input, Class<T> responseClass) throws WsException {
        return RestClient.sendRequest(getConfiguration(), url, httpMethod, headers, input, responseClass);
    }
}