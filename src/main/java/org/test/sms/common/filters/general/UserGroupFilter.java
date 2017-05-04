package org.test.sms.common.filters.general;

import org.test.sms.common.filters.AbstractFilter;

import java.util.List;

public class UserGroupFilter extends AbstractFilter {

    private List<String> names;

    public List<String> getNames() {
        return names;
    }

    public void setNames(List<String> names) {
        this.names = names;
    }
}