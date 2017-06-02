package org.test.sms.common.filter.university;

import org.test.sms.common.filter.AbstractFilter;

import java.util.List;

public class LecturerFilter extends AbstractFilter {

    private List<String> firstNames;

    private List<String> lastNames;

    private String personalNumber;

    public List<String> getFirstNames() {
        return firstNames;
    }

    public void setFirstNames(List<String> firstNames) {
        this.firstNames = firstNames;
    }

    public List<String> getLastNames() {
        return lastNames;
    }

    public void setLastNames(List<String> lastNames) {
        this.lastNames = lastNames;
    }

    public String getPersonalNumber() {
        return personalNumber;
    }

    public void setPersonalNumber(String personalNumber) {
        this.personalNumber = personalNumber;
    }
}