package org.test.sms.common.filter.university;

import org.test.sms.common.entity.university.Building;
import org.test.sms.common.filter.AbstractFilter;

public class AuditoriumFilter extends AbstractFilter {

    private Building building;

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }
}