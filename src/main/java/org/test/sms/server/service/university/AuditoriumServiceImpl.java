package org.test.sms.server.service.university;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.test.sms.common.entity.university.Auditorium;
import org.test.sms.common.service.university.AuditoriumService;
import org.test.sms.server.dao.interfaces.university.AuditoriumDao;
import org.test.sms.server.service.general.AbstractServiceImpl;

@Service
@Transactional
public class AuditoriumServiceImpl extends AbstractServiceImpl<Auditorium> implements AuditoriumService {

    @Autowired
    public AuditoriumServiceImpl(AuditoriumDao dao) {
        super(dao);
    }
}