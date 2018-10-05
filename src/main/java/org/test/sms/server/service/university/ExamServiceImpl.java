package org.test.sms.server.service.university;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.test.sms.common.entity.university.Exam;
import org.test.sms.common.service.university.ExamService;
import org.test.sms.server.dao.interfaces.university.ExamDao;
import org.test.sms.server.service.general.AbstractServiceImpl;

@Service
@Transactional
public class ExamServiceImpl extends AbstractServiceImpl<Exam> implements ExamService {

    @Autowired
    public ExamServiceImpl(ExamDao dao) {
        super(dao);
    }
}