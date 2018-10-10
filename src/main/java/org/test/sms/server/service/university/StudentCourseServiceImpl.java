package org.test.sms.server.service.university;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.test.sms.common.entity.university.StudentCourse;
import org.test.sms.common.service.university.StudentCourseService;
import org.test.sms.server.dao.interfaces.university.StudentCourseDao;
import org.test.sms.server.service.general.AbstractServiceImpl;

@Service
@Transactional
public class StudentCourseServiceImpl extends AbstractServiceImpl<StudentCourse> implements StudentCourseService {

    @Autowired
    public StudentCourseServiceImpl(StudentCourseDao dao) {
        super(dao);
    }
}