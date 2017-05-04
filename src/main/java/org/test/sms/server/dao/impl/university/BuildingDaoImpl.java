package org.test.sms.server.dao.impl.university;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.test.sms.common.entities.university.Auditorium;
import org.test.sms.common.entities.university.Building;
import org.test.sms.common.filters.AbstractFilter;
import org.test.sms.common.filters.university.AuditoriumFilter;
import org.test.sms.server.dao.impl.AbstractDaoImpl;
import org.test.sms.server.dao.interfaces.university.AuditoriumDao;
import org.test.sms.server.dao.interfaces.university.BuildingDao;

import java.util.List;
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

        result.ifPresent(e -> {
            List<Auditorium> auditoriums = e.getAuditoriums();
            auditoriums.size();
            auditoriums.forEach(f -> f.getSeats().size());
        });

        return result;
    }

    @Override
    public List<Building> getList(AbstractFilter filter) {
        List<Building> result = em.createQuery("SELECT new Building(id, name, address, lat, lon) FROM Building", Building.class).getResultList();

        result.forEach(e -> {
            AuditoriumFilter auditoriumFilter = new AuditoriumFilter();
            auditoriumFilter.setBuilding(e);

            e.setAuditoriums(auditoriumDao.getList(auditoriumFilter));
        });

        return result;
    }
}