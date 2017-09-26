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
import java.util.Map;
import java.util.Optional;

@Repository
public class BuildingDaoImpl extends AbstractDaoImpl<Building> implements BuildingDao {

    private AuditoriumDao auditoriumDao;

    @Autowired
    public BuildingDaoImpl(AuditoriumDao auditoriumDao) {
        this.auditoriumDao = auditoriumDao;
    }

    @Override
    public Optional<Building> get(long id) {
        Optional<Building> result = super.get(id);

        result.ifPresent(building -> {
            List<Auditorium> auditoriums = building.getAuditoriums();
            auditoriums.size();
            auditoriums.forEach(auditorium -> auditorium.getSeats().size());
        });

        return result;
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

    @Override
    protected Building init(Building entity) {
        return entity;
    }

    @Override
    protected String getSelect() {
        return "id, name, address, lat, lon";
    }

    @Override
    protected void addFilter(StringBuilder queryBuilder, Map<String, Object> params, AbstractFilter abstractFilter) {}

    @Override
    protected String getOrderBy() {
        return "name";
    }
}