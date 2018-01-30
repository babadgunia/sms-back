package org.test.sms.server.service.university;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.test.sms.common.entity.university.Exam;
import org.test.sms.common.exception.AppException;
import org.test.sms.common.filter.general.AbstractFilter;
import org.test.sms.common.service.university.ExamService;
import org.test.sms.server.dao.interfaces.university.ExamDao;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ExamServiceImpl implements ExamService {

    private ExamDao dao;

    @Autowired
    public ExamServiceImpl(ExamDao dao) {
        this.dao = dao;
    }

    @Override
    public Exam add(Exam entity) throws AppException {
        return dao.add(entity);
    }

    @Override
    public Exam update(Exam entity) throws AppException {
        return dao.update(entity);
    }

    @Override
    public void delete(long id) throws AppException {
        dao.delete(id);
    }

    @Override
    public Optional<Exam> get(long id) {
        return dao.get(id);
    }

    @Override
    public long getCount(AbstractFilter filter) {
        return dao.getCount(filter);
    }

    @Override
    public List<Exam> getList(AbstractFilter filter) {
        return dao.getList(filter);
    }
}