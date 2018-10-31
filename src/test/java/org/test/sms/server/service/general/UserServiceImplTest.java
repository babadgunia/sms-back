package org.test.sms.server.service.general;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.test.sms.common.entity.general.User;
import org.test.sms.common.entity.general.UserGroup;
import org.test.sms.common.filter.general.UserFilter;
import org.test.sms.common.service.general.UserService;
import org.test.sms.server.dao.interfaces.general.UserDao;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
public class UserServiceImplTest {

    private UserService userService;

    @MockBean
    private UserDao userDao;

    @Before
    public void setUp() {
        userService = new UserServiceImpl(userDao, null, null);
    }

    @Test
    public void whenValidUsername_thenUserShouldBeFound() {
        String name = "Firstname Lastname";

        UserGroup userGroup = new UserGroup();

        userGroup.setId(1);
        userGroup.setName("UserGroup");

        User user = new User();
        user.setUsername("test");
        user.setName(name);
        user.setUserGroup(userGroup);

        UserFilter userFilter = new UserFilter();
        userFilter.setUsername("test");
        Mockito.when(userDao.get(userFilter)).thenReturn(Optional.of(user));

        Optional<User> foundUser = userService.get(userFilter);

        assertEquals(foundUser.get().getName(), name);
    }
}
