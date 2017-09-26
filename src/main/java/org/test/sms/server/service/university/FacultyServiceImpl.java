package org.test.sms.server.service.university;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.test.sms.common.entity.university.Faculty;
import org.test.sms.common.exception.AppException;
import org.test.sms.common.filter.general.AbstractFilter;
import org.test.sms.common.service.university.FacultyService;
import org.test.sms.server.dao.interfaces.university.FacultyDao;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FacultyServiceImpl implements FacultyService {

    private FacultyDao dao;

    @Autowired
    public FacultyServiceImpl(FacultyDao dao) {
        this.dao = dao;
    }

    @Override
    public Faculty add(Faculty entity) throws AppException {
        return dao.add(entity);
    }

    @Override
    public Faculty update(Faculty entity) throws AppException {
        return dao.update(entity);
    }

    @Override
    public void delete(long id) throws AppException {
        dao.delete(id);
    }

    @Override
    public Optional<Faculty> get(long id) {
        return dao.get(id);
    }

    @Override
    public long getCount(AbstractFilter filter) {
        return dao.getCount(filter);
    }

    @Override
    public List<Faculty> getList(AbstractFilter filter) {
        return dao.getList(filter);
    }
}