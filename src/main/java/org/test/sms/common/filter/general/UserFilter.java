package org.test.sms.common.filter.general;

import org.test.sms.common.entity.general.UserGroup;
import org.test.sms.common.filter.AbstractFilter;

import java.util.List;

public class UserFilter extends AbstractFilter {

    private List<String> names;

    private String username;

    private UserGroup userGroup;

    public List<String> getNames() {
        return names;
    }

    public void setNames(List<String> names) {
        this.names = names;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserGroup getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(UserGroup userGroup) {
        this.userGroup = userGroup;
    }
}