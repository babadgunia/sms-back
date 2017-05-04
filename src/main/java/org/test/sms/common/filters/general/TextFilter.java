package org.test.sms.common.filters.general;

import org.test.sms.common.filters.AbstractFilter;

public class TextFilter extends AbstractFilter {

    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}