package org.test.sms.server.dao.impl.university;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.test.sms.common.entity.university.Faculty;
import org.test.sms.common.filter.general.AbstractFilter;
import org.test.sms.common.filter.university.CourseFilter;
import org.test.sms.common.utils.Utils;
import org.test.sms.server.dao.impl.general.AbstractDaoImpl;
import org.test.sms.server.dao.interfaces.university.CourseDao;
import org.test.sms.server.dao.interfaces.university.FacultyDao;
import org.test.sms.server.dao.interfaces.university.StudentDao;

import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class FacultyDaoImpl extends AbstractDaoImpl<Faculty> implements FacultyDao {

    private StudentDao studentDao;

    private CourseDao courseDao;

    @Autowired
    public FacultyDaoImpl(StudentDao studentDao, CourseDao courseDao) {
        this.studentDao = studentDao;
        this.courseDao = courseDao;
    }

    public boolean exists(String name) {
        TypedQuery<Faculty> query = em.createQuery("SELECT new Faculty(id) FROM Faculty WHERE UPPER(name) = :name", Faculty.class);
        query.setParameter("name", name.toUpperCase());

        return !Utils.isBlank(query.getResultList());
    }

    @Override
    public Optional<Faculty> get(long id) {
        Optional<Faculty> result = super.get(id);

        result.ifPresent(faculty -> faculty.getCourses().size());

        return result;
    }

    @Override
    public List<Faculty> getList(AbstractFilter filter) {
        List<Faculty> result = super.getList(filter);

        result.forEach(faculty -> {
            CourseFilter courseFilter = new CourseFilter();
            courseFilter.setFaculty(faculty);

            faculty.setCourses(courseDao.getList(courseFilter));
        });

        return result;
    }
}