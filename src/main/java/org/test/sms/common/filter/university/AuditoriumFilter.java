package org.test.sms.common.filter.university;

import lombok.Getter;
import lombok.Setter;
import org.test.sms.common.entity.university.Building;
import org.test.sms.common.filter.general.AbstractFilter;

@Getter @Setter
public class AuditoriumFilter extends AbstractFilter {

    private Building building;
}