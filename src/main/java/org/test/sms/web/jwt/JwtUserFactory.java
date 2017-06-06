package org.test.sms.web.jwt;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.test.sms.common.entity.general.User;
import org.test.sms.common.entity.general.UserGroup;

import java.util.Collections;
import java.util.List;

public class JwtUserFactory {

    private JwtUserFactory() {
    }

    public static JwtUser create(User user) {
        return new JwtUser(
                user.getId(),
                user.getUsername(),
                user.getName(),
                user.getPassword(),
                mapToGrantedAuthorities(user.getUserGroup()),
                true
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(UserGroup userGroup) {
        return Collections.singletonList(new SimpleGrantedAuthority(userGroup.getName()));
    }
}