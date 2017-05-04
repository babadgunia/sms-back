package org.test.sms.common.filters.university;

import org.test.sms.common.entities.university.Building;
import org.test.sms.common.filters.AbstractFilter;

public class AuditoriumFilter extends AbstractFilter {

    private Building building;

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }
}