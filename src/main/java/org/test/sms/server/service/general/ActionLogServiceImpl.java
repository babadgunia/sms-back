package org.test.sms.server.service.general;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.test.sms.common.entity.general.ActionLog;
import org.test.sms.common.service.general.ActionLogService;
import org.test.sms.server.dao.interfaces.general.ActionLogDao;

@Service
@Transactional
public class ActionLogServiceImpl extends AbstractServiceImpl<ActionLog> implements ActionLogService {

    @Autowired
    public ActionLogServiceImpl(ActionLogDao dao) {
        super(dao);
    }
}