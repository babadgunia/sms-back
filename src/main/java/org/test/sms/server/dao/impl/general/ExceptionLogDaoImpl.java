package org.test.sms.server.dao.impl.general;

import org.springframework.stereotype.Repository;
import org.test.sms.common.entity.general.ExceptionLog;
import org.test.sms.server.dao.interfaces.general.ExceptionLogDao;

@Repository
public class ExceptionLogDaoImpl extends AbstractDaoImpl<ExceptionLog> implements ExceptionLogDao {}