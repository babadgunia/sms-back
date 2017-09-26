package org.test.sms.server.service.university;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.test.sms.common.entity.university.Lecturer;
import org.test.sms.common.exception.AppException;
import org.test.sms.common.filter.general.AbstractFilter;
import org.test.sms.common.service.university.LecturerService;
import org.test.sms.server.dao.interfaces.university.LecturerDao;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class LecturerServiceImpl implements LecturerService {

    private LecturerDao dao;

    @Autowired
    public LecturerServiceImpl(LecturerDao dao) {
        this.dao = dao;
    }

    @Override
    public Lecturer add(Lecturer entity) throws AppException {
        return dao.add(entity);
    }

    @Override
    public Lecturer update(Lecturer entity) throws AppException {
        return dao.update(entity);
    }

    @Override
    public void delete(long id) throws AppException {
        dao.delete(id);
    }

    @Override
    public Optional<Lecturer> get(long id) {
        return dao.get(id);
    }

    @Override
    public long getCount(AbstractFilter filter) {
        return dao.getCount(filter);
    }

    @Override
    public List<Lecturer> getList(AbstractFilter filter) {
        return dao.getList(filter);
    }

    @Override
    public Optional<Lecturer> getByUserId(long userId) {
        return dao.getByUserId(userId);
    }
}