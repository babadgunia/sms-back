package org.test.sms.server.dao.impl.general;

import org.springframework.stereotype.Repository;
import org.test.sms.common.entity.general.ActionLog;
import org.test.sms.server.dao.interfaces.general.ActionLogDao;

@Repository
public class ActionLogDaoImpl extends AbstractDaoImpl<ActionLog> implements ActionLogDao {}