package org.test.sms.server.dao.impl.university;

import org.springframework.stereotype.Repository;
import org.test.sms.common.entities.university.Group;
import org.test.sms.server.dao.impl.AbstractDaoImpl;
import org.test.sms.server.dao.interfaces.university.GroupDao;

@Repository
public class GroupDaoImpl extends AbstractDaoImpl<Group> implements GroupDao {
}