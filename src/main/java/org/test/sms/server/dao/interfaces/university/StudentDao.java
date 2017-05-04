package org.test.sms.server.dao.interfaces.university;

import org.test.sms.common.entities.university.Student;
import org.test.sms.server.dao.AbstractDao;

import java.util.Optional;

public interface StudentDao extends AbstractDao<Student> {

    Optional<Student> getByUserId(long userId);

    boolean exists(long facultyId);
}