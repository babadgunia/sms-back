package org.test.sms.server.service.university;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.test.sms.common.entity.university.Auditorium;
import org.test.sms.common.exception.AppException;
import org.test.sms.common.filter.general.AbstractFilter;
import org.test.sms.common.service.university.AuditoriumService;
import org.test.sms.server.dao.interfaces.university.AuditoriumDao;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AuditoriumServiceImpl implements AuditoriumService {

    private AuditoriumDao dao;

    @Autowired
    public AuditoriumServiceImpl(AuditoriumDao dao) {
        this.dao = dao;
    }

    @Override
    public Auditorium add(Auditorium entity) throws AppException {
        return dao.add(entity);
    }

    @Override
    public Auditorium update(Auditorium entity) throws AppException {
        return dao.update(entity);
    }

    @Override
    public void delete(long id) throws AppException {
        dao.delete(id);
    }

    @Override
    public Optional<Auditorium> get(long id) {
        return dao.get(id);
    }

    @Override
    public long getCount(AbstractFilter filter) {
        return dao.getCount(filter);
    }

    @Override
    public List<Auditorium> getList(AbstractFilter filter) {
        return dao.getList(filter);
    }
}