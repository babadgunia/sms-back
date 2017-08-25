package org.test.sms.common.filter.general;

import org.test.sms.common.filter.AbstractFilter;

import java.util.List;

public class UserFilter extends AbstractFilter {

    private Long id;

    private String username;

    private List<String> names;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getNames() {
        return names;
    }

    public void setNames(List<String> names) {
        this.names = names;
    }
}