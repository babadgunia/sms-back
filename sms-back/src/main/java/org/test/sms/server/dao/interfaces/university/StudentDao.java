package org.test.sms.server.dao.interfaces.university;

import org.test.sms.common.entity.university.Student;
import org.test.sms.server.dao.interfaces.general.AbstractDao;

import java.util.Optional;

public interface StudentDao extends AbstractDao<Student> {

    Optional<Student> getByUserId(long userId);

    boolean exists(long facultyId);
}