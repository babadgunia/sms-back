package org.test.sms.common.filter.general;

import lombok.Getter;
import lombok.Setter;
import org.test.sms.common.enums.general.LanguageType;
import org.test.sms.common.enums.general.StatusType;

import java.util.List;

@Getter @Setter
public class UserFilter extends AbstractFilter {

    private Long id;

    private String username;

    private String email;

    private List<String> names;

    private StatusType status;

    private LanguageType language;

    private Long userGroupId;
}