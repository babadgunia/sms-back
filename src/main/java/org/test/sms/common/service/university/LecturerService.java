package org.test.sms.common.service.university;

import org.test.sms.common.entities.university.Lecturer;
import org.test.sms.common.service.AbstractService;

import java.util.Optional;

public interface LecturerService extends AbstractService<Lecturer> {

    Optional<Lecturer> getByUserId(long userId);
}