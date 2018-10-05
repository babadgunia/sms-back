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
import java.util.Optional;

@Repository
public class CourseDaoImpl extends AbstractDaoImpl<Course> implements CourseDao {

    @Override
    public Optional<Course> get(long id) {
        Optional<Course> result = super.get(id);

        result.ifPresent(course -> course.getModules().size());

        return result;
    }

    @Override
    protected Course init(Course entity) {
        return entity;
    }

    @Override
    protected String getSelect() {
        return null;
    }

    @Override
    protected void addFilter(StringBuilder queryBuilder, Map<String, Object> params, AbstractFilter abstractFilter) {
        CourseFilter filter = (CourseFilter) abstractFilter;

        Faculty faculty = filter.getFaculty();
        if (Objects.nonNull(faculty)) {
            queryBuilder.append(" AND faculty = :faculty");
            params.put("faculty", faculty);
        }
    }

    @Override
    protected String getOrderBy() {
        return "name";
    }
}