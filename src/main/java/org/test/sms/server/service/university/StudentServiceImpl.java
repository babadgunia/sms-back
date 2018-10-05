package org.test.sms.server.service.university;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.test.sms.common.entity.university.Student;
import org.test.sms.common.enums.general.ErrorCodeType;
import org.test.sms.common.exception.AppException;
import org.test.sms.common.service.university.StudentService;
import org.test.sms.server.dao.interfaces.university.StudentDao;
import org.test.sms.server.service.general.AbstractServiceImpl;

import java.util.Optional;

@Service
@Transactional
public class StudentServiceImpl extends AbstractServiceImpl<Student> implements StudentService {

    @Autowired
    public StudentServiceImpl(StudentDao dao) {
        super(dao);
    }

    @Override
    protected void validateSave(Student entity) throws AppException {
        String personalNumber = entity.getPersonalNumber();
        if (((StudentDao) dao).exists(personalNumber)) {
            throw new AppException(ErrorCodeType.STUDENT_EXISTS, personalNumber);
        }
    }

    @Override
    public Optional<Student> getByUserId(long userId) {
        return ((StudentDao) dao).getByUserId(userId);
    }
}