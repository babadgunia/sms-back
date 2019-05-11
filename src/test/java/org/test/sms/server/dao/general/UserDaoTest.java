package org.test.sms.server.dao.general;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;
import org.test.sms.common.entity.general.User;
import org.test.sms.common.entity.general.UserGroup;
import org.test.sms.server.dao.impl.general.UserDaoImpl;

import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
@ComponentScan(basePackages = {"org.test.sms"})
public class UserDaoTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserDaoImpl userDao;

    @Test
    public void whenUsernameExists_thenReturnTrue() {
        User u = new User();
        u.setUsername("Test");
        u.setCreated(ZonedDateTime.now());
        u.setLastModified(ZonedDateTime.now());

        UserGroup ug = new UserGroup();
        ug.setCreated(ZonedDateTime.now());
        ug.setLastModified(ZonedDateTime.now());
        entityManager.persist(ug);
        entityManager.flush();
        u.setUserGroup(ug);

        entityManager.persist(u);
        entityManager.flush();

        assertTrue(userDao.exists(u.getUsername()));
    }
}