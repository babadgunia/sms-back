package org.test.sms.server.dao.impl.university;

import org.springframework.stereotype.Repository;
import org.test.sms.common.entity.university.StudentCourse;
import org.test.sms.common.filter.AbstractFilter;
import org.test.sms.server.dao.impl.AbstractDaoImpl;
import org.test.sms.server.dao.interfaces.university.StudentCourseDao;

import java.util.List;
import java.util.Map;

@Repository
public class StudentCourseDaoImpl extends AbstractDaoImpl<StudentCourse> implements StudentCourseDao {

    @Override
    public List<StudentCourse> getList(AbstractFilter filter) {
        return null;
    }

    @Override
    protected void addFilter(StringBuilder queryBuilder, Map<String, Object> params, AbstractFilter abstractFilter) {}
}