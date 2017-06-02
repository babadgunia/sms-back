package org.test.sms.server.service.general;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.test.sms.common.entity.general.Hero;
import org.test.sms.common.exception.AppException;
import org.test.sms.common.filter.AbstractFilter;
import org.test.sms.common.service.general.HeroService;
import org.test.sms.server.dao.interfaces.general.HeroDao;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class HeroServiceImpl implements HeroService {

    private HeroDao dao;

    @Autowired
    public HeroServiceImpl(HeroDao dao) {
        this.dao = dao;
    }

    @Override
    public Hero add(Hero entity) throws AppException {
        return dao.add(entity);
    }

    @Override
    public Hero update(Hero entity) throws AppException {
        return dao.update(entity);
    }

    @Override
    public void delete(long id) throws AppException {
        dao.delete(id);
    }

    @Override
    public Optional<Hero> get(long id) {
        return dao.get(id);
    }

    @Override
    public List<Hero> getList(AbstractFilter filter) {
        return dao.getList(filter);
    }
}