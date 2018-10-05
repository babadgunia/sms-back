package org.test.sms.common.service.university;

import org.test.sms.common.entity.university.Lecturer;
import org.test.sms.common.service.general.AbstractService;

import java.util.Optional;

public interface LecturerService extends AbstractService<Lecturer> {

    Optional<Lecturer> getByUserId(long userId);
}