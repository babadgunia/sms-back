package org.test.sms.server.service.university;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.test.sms.common.entity.university.StudentCourse;
import org.test.sms.common.exception.AppException;
import org.test.sms.common.filter.general.AbstractFilter;
import org.test.sms.common.service.university.StudentCourseService;
import org.test.sms.server.dao.interfaces.university.StudentCourseDao;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StudentCourseServiceImpl implements StudentCourseService {

    private StudentCourseDao dao;

    @Autowired
    public StudentCourseServiceImpl(StudentCourseDao dao) {
        this.dao = dao;
    }

    @Override
    public StudentCourse add(StudentCourse entity) throws AppException {
        return dao.add(entity);
    }

    @Override
    public StudentCourse update(StudentCourse entity) throws AppException {
        return dao.update(entity);
    }

    @Override
    public void delete(long id) throws AppException {
        dao.delete(id);
    }

    @Override
    public Optional<StudentCourse> get(long id) {
        return dao.get(id);
    }

    @Override
    public long getCount(AbstractFilter filter) {
        return dao.getCount(filter);
    }

    @Override
    public List<StudentCourse> getList(AbstractFilter filter) {
        return dao.getList(filter);
    }
}