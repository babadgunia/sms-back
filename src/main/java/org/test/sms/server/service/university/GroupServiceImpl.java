package org.test.sms.server.service.university;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.test.sms.common.entity.university.Group;
import org.test.sms.common.exception.AppException;
import org.test.sms.common.filter.general.AbstractFilter;
import org.test.sms.common.service.university.GroupService;
import org.test.sms.server.dao.interfaces.university.GroupDao;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class GroupServiceImpl implements GroupService {

    private GroupDao dao;

    @Autowired
    public GroupServiceImpl(GroupDao dao) {
        this.dao = dao;
    }

    @Override
    public Group add(Group entity) throws AppException {
        return dao.add(entity);
    }

    @Override
    public Group update(Group entity) throws AppException {
        return dao.update(entity);
    }

    @Override
    public void delete(long id) throws AppException {
        dao.delete(id);
    }

    @Override
    public Optional<Group> get(long id) {
        return dao.get(id);
    }

    @Override
    public long getCount(AbstractFilter filter) {
        return dao.getCount(filter);
    }

    @Override
    public List<Group> getList(AbstractFilter filter) {
        return dao.getList(filter);
    }
}