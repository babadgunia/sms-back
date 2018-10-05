package org.test.sms.server.dao.impl.university;

import org.springframework.stereotype.Repository;
import org.test.sms.common.entity.university.Building;
import org.test.sms.common.entity.university.Course;
import org.test.sms.common.entity.university.Exam;
import org.test.sms.common.entity.university.Faculty;
import org.test.sms.common.enums.university.ExamType;
import org.test.sms.common.filter.general.AbstractFilter;
import org.test.sms.common.filter.university.ExamFilter;
import org.test.sms.server.dao.impl.general.AbstractDaoImpl;
import org.test.sms.server.dao.interfaces.university.ExamDao;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

@Repository
public class ExamDaoImpl extends AbstractDaoImpl<Exam> implements ExamDao {

    @Override
    protected Exam init(Exam entity) {
        return entity;
    }

    @Override
    protected String getSelect() {
        return null;
    }

    @Override
    protected void addFilter(StringBuilder queryBuilder, Map<String, Object> params, AbstractFilter abstractFilter) {
        ExamFilter filter = (ExamFilter) abstractFilter;

        Faculty faculty = filter.getFaculty();
        if (Objects.nonNull(faculty)) {
            queryBuilder.append(" AND course.faculty = :faculty");
            params.put("faculty", faculty);
        }

        Course course = filter.getCourse();
        if (Objects.nonNull(course)) {
            queryBuilder.append(" AND course = :course");
            params.put("course", course);
        }

        LocalDateTime from = filter.getFromDate();
        if (Objects.nonNull(from)) {
            queryBuilder.append(" AND startDate >= :from");
            params.put("from", from);
        }

        LocalDateTime to = filter.getToDate();
        if (Objects.nonNull(to)) {
            queryBuilder.append(" AND startDate <= :to");
            params.put("to", to);
        }

        Building building = filter.getBuilding();
        if (Objects.nonNull(building)) {
            queryBuilder.append(" AND building = :building");
            params.put("building", building);
        }

        ExamType type = filter.getType();
        if (Objects.nonNull(type)) {
            queryBuilder.append(" AND type = :type");
            params.put("type", type);
        }
    }

    @Override
    protected String getOrderBy() {
        return "startDate";
    }
}