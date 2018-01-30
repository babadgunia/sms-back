package org.test.sms.server.service.general;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.test.sms.common.entity.general.Permission;
import org.test.sms.common.entity.general.User;
import org.test.sms.common.enums.general.PermissionType;
import org.test.sms.common.enums.general.StatusType;
import org.test.sms.server.dao.interfaces.general.UserDao;
import org.test.sms.web.jwt.JwtUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserDetailsServiceJwtImpl implements UserDetailsService {

    private UserDao userDao;

    @Autowired
    public UserDetailsServiceJwtImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userWrapper = userDao.getForAuthByUsername(username);

        if (!userWrapper.isPresent()) {
            throw new UsernameNotFoundException("username [" + username + "] not found");
        } else {
            User user = userWrapper.get();
            JwtUser jwtUser = new JwtUser();

            jwtUser.setUsername(username);
            jwtUser.setPassword(user.getPassword());
            jwtUser.setEnabled(user.getStatus() == StatusType.ACTIVE);

            List<SimpleGrantedAuthority> permissions = new ArrayList<>();
            for (Permission permission : user.getUserGroup().getPermissions()) {
                for (PermissionType permissionType : permission.getPermissionTypes()) {
                    permissions.add(new SimpleGrantedAuthority(permission.getPermissionGroup().toString() + "." + permissionType.toString()));
                }
            }
            jwtUser.setAuthorities(permissions);
            return jwtUser;
        }
    }
}