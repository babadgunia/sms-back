package org.test.sms.server.service.university;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.test.sms.common.entity.university.Course;
import org.test.sms.common.exception.AppException;
import org.test.sms.common.filter.general.AbstractFilter;
import org.test.sms.common.service.university.CourseService;
import org.test.sms.server.dao.interfaces.university.CourseDao;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CourseServiceImpl implements CourseService {

    private CourseDao dao;

    @Autowired
    public CourseServiceImpl(CourseDao dao) {
        this.dao = dao;
    }

    @Override
    public Course add(Course entity) throws AppException {
        return dao.add(entity);
    }

    @Override
    public Course update(Course entity) throws AppException {
        return dao.update(entity);
    }

    @Override
    public void delete(long id) throws AppException {
        dao.delete(id);
    }

    @Override
    public Optional<Course> get(long id) {
        return dao.get(id);
    }

    @Override
    public long getCount(AbstractFilter filter) {
        return dao.getCount(filter);
    }

    @Override
    public List<Course> getList(AbstractFilter filter) {
        return dao.getList(filter);
    }
}