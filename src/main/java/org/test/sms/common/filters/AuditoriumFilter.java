package org.test.sms.common.filters;

import org.test.sms.common.entities.university.Building;

public class AuditoriumFilter extends AbstractFilter {

    private Building building;

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }
}