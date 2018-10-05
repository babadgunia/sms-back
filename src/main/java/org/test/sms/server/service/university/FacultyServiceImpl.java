package org.test.sms.server.service.university;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.test.sms.common.entity.university.Faculty;
import org.test.sms.common.enums.general.ErrorCodeType;
import org.test.sms.common.exception.AppException;
import org.test.sms.common.service.university.FacultyService;
import org.test.sms.server.dao.interfaces.university.FacultyDao;
import org.test.sms.server.dao.interfaces.university.StudentDao;
import org.test.sms.server.service.general.AbstractServiceImpl;

@Service
@Transactional
public class FacultyServiceImpl extends AbstractServiceImpl<Faculty> implements FacultyService {

    private StudentDao studentDao;

    @Autowired
    public FacultyServiceImpl(FacultyDao dao, StudentDao studentDao) {
        super(dao);

        this.studentDao = studentDao;
    }

    @Override
    protected void validateSave(Faculty entity) throws AppException {
        String name = entity.getName();
        if (((FacultyDao) dao).exists(name)) {
            throw new AppException(ErrorCodeType.FACULTY_EXISTS, name);
        }
    }

    @Override
    protected void validateDelete(long id) throws AppException {
        if (studentDao.exists(id)) {
            throw new AppException(ErrorCodeType.FACULTY_ASSIGNED_TO_STUDENT);
        }
    }
}