package org.test.sms.server.service.university;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.test.sms.common.entity.university.Course;
import org.test.sms.common.service.university.CourseService;
import org.test.sms.server.dao.interfaces.university.CourseDao;
import org.test.sms.server.service.general.AbstractServiceImpl;

@Service
@Transactional
public class CourseServiceImpl extends AbstractServiceImpl<Course> implements CourseService {

    @Autowired
    public CourseServiceImpl(CourseDao dao) {
        super(dao);
    }
}