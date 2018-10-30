package org.test.sms.server.dao.impl.university;

import org.springframework.stereotype.Repository;
import org.test.sms.common.entity.university.Course;
import org.test.sms.common.entity.university.Faculty;
import org.test.sms.common.filter.general.AbstractFilter;
import org.test.sms.common.filter.university.CourseFilter;
import org.test.sms.server.dao.impl.general.AbstractDaoImpl;
import org.test.sms.server.dao.interfaces.university.CourseDao;

import java.util.Map;
import java.util.Objects;

@Repository
public class CourseDaoImpl extends AbstractDaoImpl<Course> implements CourseDao {

    @Override
    protected void initLazyFields(AbstractFilter abstractFilter, Course entity) {
        entity.getModules().size();
    }

    @Override
    protected void addFilter(AbstractFilter abstractFilter, StringBuilder queryBuilder, Map<String, Object> params) {
        CourseFilter filter = (CourseFilter) abstractFilter;

        Faculty faculty = filter.getFaculty();
        if (Objects.nonNull(faculty)) {
            queryBuilder.append(" AND faculty = :faculty");
            params.put("faculty", faculty);
        }
    }
}