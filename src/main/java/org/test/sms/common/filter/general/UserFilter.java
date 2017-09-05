package org.test.sms.common.filter.general;

import org.test.sms.common.enums.general.LanguageType;
import org.test.sms.common.enums.general.StatusType;
import org.test.sms.common.filter.AbstractFilter;

import java.util.List;

public class UserFilter extends AbstractFilter {

    private Long id;

    private String username;

    private String email;

    private List<String> names;

    private StatusType status;

    private LanguageType language;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getNames() {
        return names;
    }

    public void setNames(List<String> names) {
        this.names = names;
    }

    public StatusType getStatus() {
        return status;
    }

    public void setStatus(StatusType status) {
        this.status = status;
    }

    public LanguageType getLanguage() {
        return language;
    }

    public void setLanguage(LanguageType language) {
        this.language = language;
    }
}