package org.test.sms.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.test.sms.common.entity.general.User;
import org.test.sms.common.filter.general.UserFilter;
import org.test.sms.common.service.general.UserService;

import java.util.List;

@RestController
@RequestMapping(value = "api/user")
public class UserController {

    private UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @RequestMapping(value = "getList", method = RequestMethod.GET)
    public ResponseEntity<List<User>> getList(@RequestParam String name) {
        UserFilter filter = new UserFilter();

        return new ResponseEntity<>(service.getList(filter), HttpStatus.OK);
    }
}