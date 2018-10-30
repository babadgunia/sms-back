package org.test.sms.server.dao.impl.university;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.test.sms.common.entity.university.Auditorium;
import org.test.sms.common.entity.university.Building;
import org.test.sms.common.filter.general.AbstractFilter;
import org.test.sms.common.filter.university.AuditoriumFilter;
import org.test.sms.server.dao.impl.general.AbstractDaoImpl;
import org.test.sms.server.dao.interfaces.university.AuditoriumDao;
import org.test.sms.server.dao.interfaces.university.BuildingDao;

import java.util.List;

@Repository
public class BuildingDaoImpl extends AbstractDaoImpl<Building> implements BuildingDao {

    private AuditoriumDao auditoriumDao;

    @Autowired
    public BuildingDaoImpl(AuditoriumDao auditoriumDao) {
        this.auditoriumDao = auditoriumDao;
    }

    @Override
    protected void initLazyFields(AbstractFilter abstractFilter, Building entity) {
        List<Auditorium> auditoriums = entity.getAuditoriums();
        auditoriums.size();
        auditoriums.forEach(auditorium -> auditorium.getSeats().size());
    }

    @Override
    public List<Building> getList(AbstractFilter filter) {
        List<Building> result = super.getList(filter);

        result.forEach(building -> {
            AuditoriumFilter auditoriumFilter = new AuditoriumFilter();
            auditoriumFilter.setBuilding(building);

            building.setAuditoriums(auditoriumDao.getList(auditoriumFilter));
        });

        return result;
    }
}