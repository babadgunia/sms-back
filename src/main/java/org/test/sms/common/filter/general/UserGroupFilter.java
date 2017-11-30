package org.test.sms.common.filter.general;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class UserGroupFilter extends AbstractFilter {

    private Long id;

    private List<String> names;
}