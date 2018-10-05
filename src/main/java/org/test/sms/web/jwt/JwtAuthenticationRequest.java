package org.test.sms.web.jwt;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@Getter @Setter
public class JwtAuthenticationRequest implements Serializable {

    private String username;

    private String password;

    public JwtAuthenticationRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
}