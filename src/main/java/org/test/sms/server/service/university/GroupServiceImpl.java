package org.test.sms.server.service.university;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.test.sms.common.entity.university.Group;
import org.test.sms.common.service.university.GroupService;
import org.test.sms.server.dao.interfaces.university.GroupDao;
import org.test.sms.server.service.general.AbstractServiceImpl;

@Service
@Transactional
public class GroupServiceImpl extends AbstractServiceImpl<Group> implements GroupService {

    @Autowired
    public GroupServiceImpl(GroupDao dao) {
        super(dao);
    }
}