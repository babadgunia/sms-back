package org.test.sms.ws.rest.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.test.sms.common.utils.DateTimeUtils;
import org.test.sms.ws.rest.common.enums.HttpMethodType;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Map;

@Log4j2 class RestClient {

    static <T> T sendRequest(Configuration configuration, String url, HttpMethodType httpMethod, Map<String, String> headers, Object input, Class<T> responseClass)
            throws WsException {
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            String requestUrl = initRequestUrl(configuration, url);

            ObjectMapper objectMapper = new ObjectMapper();

            HttpRequestBase request = initRequest(requestUrl, httpMethod, headers, input, objectMapper);

            long startTime = System.currentTimeMillis();

            try (CloseableHttpResponse response = httpClient.execute(request)) {
                String responseBody = processResponse(response, configuration, startTime);

                if (responseClass != null) {
                    if (responseBody == null) {
                        throw new WsException("Received empty response from the server! Response of type " + responseClass.getSimpleName() + " is expected!");
                    }

                    return objectMapper.readValue(responseBody, responseClass);
                }

                return null;
            }
        } catch (HttpException | IOException e) {
            throw new WsException(e);
        }
    }

    private static String initRequestUrl(Configuration configuration, String url) {
        String protocol = configuration.getProtocol().getName();
        String host = configuration.getHost();
        int port = configuration.getPort();
        String baseUrl = configuration.getBaseUrl();

        return protocol + "://" + host + ":" + port + "/" + baseUrl + url;
    }

    private static HttpRequestBase initRequest(String requestUrl, HttpMethodType httpMethod, Map<String, String> headers, Object input, ObjectMapper objectMapper)
            throws JsonProcessingException {
        HttpRequestBase request = createRequest(requestUrl, httpMethod);
        log.info("request url [" + request.toString() + "]");

        headers.forEach(request::setHeader);
        log.info("request headers " + Arrays.toString(request.getAllHeaders()));

        setRequestInput(request, httpMethod, input, objectMapper);

        return request;
    }

    private static HttpRequestBase createRequest(String url, HttpMethodType httpMethod) {
        switch (httpMethod) {
            case DELETE: {
                return new HttpDelete(url);
            }
            default:
            case GET: {
                return new HttpGet(url);
            }
            case PATCH: {
                return new HttpPatch(url);
            }
            case POST: {
                return new HttpPost(url);
            }
            case PUT: {
                return new HttpPut(url);
            }
        }
    }

    private static void setRequestInput(HttpRequestBase request, HttpMethodType httpMethod, Object input, ObjectMapper objectMapper) throws JsonProcessingException {
        if (input != null && (httpMethod == HttpMethodType.PATCH || httpMethod == HttpMethodType.POST || httpMethod == HttpMethodType.PUT)) {
            String inputString = objectMapper.writeValueAsString(input);
            log.info("request input [" + inputString + "]");

            StringEntity inputEntity = new StringEntity(inputString, "UTF-8");
            ((HttpEntityEnclosingRequestBase) request).setEntity(inputEntity);
        }
    }

    private static String processResponse(CloseableHttpResponse response, Configuration configuration, long startTime) throws HttpException, IOException {
        long httpCallDuration = System.currentTimeMillis() - startTime;
        log.info("response time [" + DateTimeUtils.formatDuration(httpCallDuration) + "]");

        int responseCode = response.getStatusLine().getStatusCode();
        log.info("response code [" + responseCode + "]");

        String responseBody = readResponseBody(response);
        log.info("response body [" + responseBody + "]");

        if (!configuration.getSuccessfulResponseCodes().contains(responseCode)) {
            throw new HttpException("response code [" + responseCode + "], response body [" + responseBody + "]");
        }

        log.info("response headers " + Arrays.toString(response.getAllHeaders()));

        return responseBody;
    }

    private static String readResponseBody(CloseableHttpResponse response) throws IOException {
        HttpEntity responseEntity = response.getEntity();
        if (responseEntity == null) {
            return null;
        }

        InputStream contentStream = responseEntity.getContent();

        return IOUtils.toString(contentStream, "UTF-8");
    }
}