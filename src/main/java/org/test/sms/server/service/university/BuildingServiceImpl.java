package org.test.sms.server.service.university;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.test.sms.common.entity.university.Building;
import org.test.sms.common.service.university.BuildingService;
import org.test.sms.server.dao.interfaces.university.BuildingDao;
import org.test.sms.server.service.general.AbstractServiceImpl;

@Service
@Transactional
public class BuildingServiceImpl extends AbstractServiceImpl<Building> implements BuildingService {

    @Autowired
    public BuildingServiceImpl(BuildingDao dao) {
        super(dao);
    }
}