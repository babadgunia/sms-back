package org.test.sms.web.jwt;

import java.io.Serializable;

public class JwtAuthenticationResponse implements Serializable {

    private String token;

    public JwtAuthenticationResponse() {
    }

    public JwtAuthenticationResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}