package org.test.sms.server.dao.impl.university;

import org.springframework.stereotype.Repository;
import org.test.sms.common.entity.university.StudentCourse;
import org.test.sms.common.filter.AbstractFilter;
import org.test.sms.server.dao.impl.AbstractDaoImpl;
import org.test.sms.server.dao.interfaces.university.StudentCourseDao;

import java.util.Map;

@Repository
public class StudentCourseDaoImpl extends AbstractDaoImpl<StudentCourse> implements StudentCourseDao {

    @Override
    protected String getSelect() {
        return null;
    }

    @Override
    protected void addFilter(StringBuilder queryBuilder, Map<String, Object> params, AbstractFilter abstractFilter) {}

    @Override
    protected String getOrderBy() {
        return null;
    }
}