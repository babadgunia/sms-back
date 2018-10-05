package org.test.sms.web.dto.general;

import lombok.Getter;
import lombok.Setter;
import org.test.sms.common.enums.general.LanguageType;
import org.test.sms.common.enums.general.StatusType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter @Setter
public class UserDto {

    private int version;

    private long id;

    @NotBlank
    private String username;

    @NotBlank
    private String email;

    @NotBlank
    private String name;

    @NotNull
    private StatusType status;

    @NotNull
    private LanguageType language;

    @NotNull
    private UserGroupDto userGroup;
}