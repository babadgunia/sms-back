package org.test.sms.server.service.university;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.test.sms.common.entity.university.Lecturer;
import org.test.sms.common.enums.general.ErrorCodeType;
import org.test.sms.common.exception.AppException;
import org.test.sms.common.service.university.LecturerService;
import org.test.sms.server.dao.interfaces.university.LecturerDao;
import org.test.sms.server.service.general.AbstractServiceImpl;

import java.util.Optional;

@Service
@Transactional
public class LecturerServiceImpl extends AbstractServiceImpl<Lecturer> implements LecturerService {

    @Autowired
    public LecturerServiceImpl(LecturerDao dao) {
        super(dao);
    }

    @Override
    protected void validateSave(Lecturer entity) throws AppException {
        String personalNumber = entity.getPersonalNumber();
        if (((LecturerDao) dao).exists(personalNumber)) {
            throw new AppException(ErrorCodeType.LECTURER_EXISTS, personalNumber);
        }
    }

    @Override
    public Optional<Lecturer> getByUserId(long userId) {
        return ((LecturerDao) dao).getByUserId(userId);
    }
}