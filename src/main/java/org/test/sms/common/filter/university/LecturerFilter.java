package org.test.sms.common.filter.university;

import lombok.Getter;
import lombok.Setter;
import org.test.sms.common.filter.general.AbstractFilter;

import java.util.List;

@Getter @Setter
public class LecturerFilter extends AbstractFilter {

    private List<String> firstNames;

    private List<String> lastNames;

    private String personalNumber;
}