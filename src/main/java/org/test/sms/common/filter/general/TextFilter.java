package org.test.sms.common.filter.general;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TextFilter extends AbstractFilter {

    private String key;

    private String value;
}