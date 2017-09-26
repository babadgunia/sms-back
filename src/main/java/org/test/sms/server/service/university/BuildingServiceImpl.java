package org.test.sms.server.service.university;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.test.sms.common.entity.university.Building;
import org.test.sms.common.exception.AppException;
import org.test.sms.common.filter.general.AbstractFilter;
import org.test.sms.common.service.university.BuildingService;
import org.test.sms.server.dao.interfaces.university.BuildingDao;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BuildingServiceImpl implements BuildingService {

    private BuildingDao dao;

    @Autowired
    public BuildingServiceImpl(BuildingDao dao) {
        this.dao = dao;
    }

    @Override
    public Building add(Building entity) throws AppException {
        return dao.add(entity);
    }

    @Override
    public Building update(Building entity) throws AppException {
        return dao.update(entity);
    }

    @Override
    public void delete(long id) throws AppException {
        dao.delete(id);
    }

    @Override
    public Optional<Building> get(long id) {
        return dao.get(id);
    }

    @Override
    public long getCount(AbstractFilter filter) {
        return dao.getCount(filter);
    }

    @Override
    public List<Building> getList(AbstractFilter filter) {
        return dao.getList(filter);
    }
}