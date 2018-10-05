package org.test.sms.server.dao.impl.university;

import org.springframework.stereotype.Repository;
import org.test.sms.common.entity.university.Student;
import org.test.sms.common.filter.general.AbstractFilter;
import org.test.sms.common.filter.university.StudentFilter;
import org.test.sms.common.utils.Utils;
import org.test.sms.server.dao.impl.general.AbstractDaoImpl;
import org.test.sms.server.dao.interfaces.university.StudentDao;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Repository
public class StudentDaoImpl extends AbstractDaoImpl<Student> implements StudentDao {

    public boolean exists(String personalNumber) {
        TypedQuery<Student> query = em.createQuery("SELECT new Student(id) FROM Student WHERE UPPER(personalNumber) = :personalNumber", Student.class);
        query.setParameter("personalNumber", personalNumber);

        return !Utils.isBlank(query.getResultList());
    }

    @Override
    public Optional<Student> getByUserId(long userId) {
        TypedQuery<Student> query = em.createQuery("FROM Student WHERE user.id = :userId", Student.class);
        query.setParameter("userId", userId);

        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public boolean exists(long facultyId) {
        TypedQuery<Student> query = em.createQuery("SELECT new Student(id) FROM Student WHERE major.id = :facultyId OR minor.id = :facultyId", Student.class);
        query.setParameter("facultyId", facultyId);

        return !Utils.isBlank(query.getResultList());
    }

    @Override
    protected void addFilter(StringBuilder queryBuilder, Map<String, Object> params, AbstractFilter abstractFilter) {
        StudentFilter filter = (StudentFilter) abstractFilter;

        List<String> firstNames = filter.getFirstNames();
        if (Objects.nonNull(firstNames)) {
            queryBuilder.append(" AND firstName IN(:firstNames)");
            params.put("firstNames", firstNames);
        }

        List<String> lastNames = filter.getLastNames();
        if (Objects.nonNull(lastNames)) {
            queryBuilder.append(" AND lastName IN(:lastNames)");
            params.put("lastNames", lastNames);
        }

        String personalNumber = filter.getPersonalNumber();
        if (Objects.nonNull(personalNumber)) {
            queryBuilder.append(" AND personalNumber LIKE :personalNumber");
            params.put("personalNumber", "%" + personalNumber + "%");
        }
    }
}