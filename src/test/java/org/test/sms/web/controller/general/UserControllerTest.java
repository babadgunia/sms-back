package org.test.sms.web.controller.general;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.test.sms.common.entity.general.User;
import org.test.sms.common.service.general.UserService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = UserController.class, secure = false)
@ContextConfiguration
public class UserControllerTest {

    static class TestConfig {
        @Bean
        public ModelMapper modelMapper() {
            return new ModelMapper();
        }
    }

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService service;

    @MockBean
    private UserDetailsService userDetailsService;

    @Test
    public void test() throws Exception {
        User user = new User();
        Mockito.when(service.add(user)).thenReturn(user);
        mvc.perform(post("api/user/add")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
