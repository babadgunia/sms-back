package org.test.sms.server.dao.impl.general;

import org.springframework.stereotype.Repository;
import org.test.sms.common.entity.general.Hero;
import org.test.sms.server.dao.impl.AbstractDaoImpl;
import org.test.sms.server.dao.interfaces.general.HeroDao;

@Repository
public class HeroDaoImpl extends AbstractDaoImpl<Hero> implements HeroDao {
}