package org.test.sms.common.filter.general;

import org.test.sms.common.filter.AbstractFilter;

import java.util.List;

public class UserGroupFilter extends AbstractFilter {

    private Long id;

    private List<String> names;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<String> getNames() {
        return names;
    }

    public void setNames(List<String> names) {
        this.names = names;
    }
}