package org.test.sms.common.service.university;

import org.test.sms.common.entities.university.Student;
import org.test.sms.common.service.AbstractService;

import java.util.Optional;

public interface StudentService extends AbstractService<Student> {

    Optional<Student> getByUserId(long userId);
}