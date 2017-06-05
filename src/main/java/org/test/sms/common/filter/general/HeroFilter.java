package org.test.sms.common.filter.general;

import org.test.sms.common.filter.AbstractFilter;

public class HeroFilter extends AbstractFilter {

    private String name;

    public HeroFilter() {
    }

    public HeroFilter(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}