package org.test.sms.web.jwt;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@Getter @Setter
public class JwtAuthenticationResponse implements Serializable {

    private String token;

    public JwtAuthenticationResponse(String token) {
        this.token = token;
    }
}