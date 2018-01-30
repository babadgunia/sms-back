package org.test.sms.server.service.general;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.test.sms.common.entity.general.ActionLog;
import org.test.sms.common.exception.AppException;
import org.test.sms.common.filter.general.AbstractFilter;
import org.test.sms.common.service.general.ActionLogService;
import org.test.sms.server.dao.interfaces.general.ActionLogDao;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ActionLogServiceImpl implements ActionLogService {

    private ActionLogDao dao;

    @Autowired
    public ActionLogServiceImpl(ActionLogDao dao) {
        this.dao = dao;
    }

    @Override
    public ActionLog add(ActionLog entity) throws AppException {
        return dao.add(entity);
    }

    @Override
    public ActionLog update(ActionLog entity) throws AppException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(long id) throws AppException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<ActionLog> get(long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long getCount(AbstractFilter filter) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<ActionLog> getList(AbstractFilter filter) {
        throw new UnsupportedOperationException();
    }
}