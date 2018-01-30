package org.test.sms.server.dao.interfaces.university;

import org.test.sms.common.entity.university.Lecturer;
import org.test.sms.server.dao.interfaces.general.AbstractDao;

import java.util.Optional;

public interface LecturerDao extends AbstractDao<Lecturer> {

    Optional<Lecturer> getByUserId(long userId);
}