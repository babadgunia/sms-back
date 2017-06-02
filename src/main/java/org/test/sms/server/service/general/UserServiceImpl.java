package org.test.sms.server.service.general;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.test.sms.common.entity.general.User;
import org.test.sms.common.exception.AppException;
import org.test.sms.common.filter.AbstractFilter;
import org.test.sms.common.service.general.UserService;
import org.test.sms.server.dao.interfaces.general.UserDao;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private UserDao dao;

    @Autowired
    public UserServiceImpl(UserDao dao) {
        this.dao = dao;
    }

    @Override
    public User add(User entity) throws AppException {
        return dao.add(entity);
    }

    @Override
    public User update(User entity) throws AppException {
        return dao.update(entity);
    }

    @Override
    public void delete(long id) throws AppException {
        dao.delete(id);
    }

    @Override
    public Optional<User> get(long id) {
        return dao.get(id);
    }

    @Override
    public List<User> getList(AbstractFilter filter) {
        return dao.getList(filter);
    }

    @Override
    public Optional<User> get(String username) {
        return dao.get(username);
    }
}