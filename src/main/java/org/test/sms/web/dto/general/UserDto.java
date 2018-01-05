package org.test.sms.web.dto.general;

import lombok.Getter;
import lombok.Setter;
import org.test.sms.common.enums.general.LanguageType;
import org.test.sms.common.enums.general.StatusType;

@Getter @Setter
public class UserDto {

    private int version;

    private long id;

    private String username;

    private String email;

    private String name;

    private StatusType status;

    private LanguageType language;

    private UserGroupDto userGroup;
}