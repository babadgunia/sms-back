package org.test.sms.common.service.university;

import org.test.sms.common.entity.university.Student;
import org.test.sms.common.service.general.AbstractService;

import java.util.Optional;

public interface StudentService extends AbstractService<Student> {

    Optional<Student> getByUserId(long userId);
}