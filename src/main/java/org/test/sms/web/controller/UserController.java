package org.test.sms.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.test.sms.common.entity.general.User;
import org.test.sms.common.exception.AppException;
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
    public ResponseEntity<List<User>> getList(@RequestParam String name, @RequestParam Long id) {
        UserFilter filter = new UserFilter();
        filter.setUsername(name);
        filter.setId(id);

        return new ResponseEntity<>(service.getList(filter), HttpStatus.OK);
    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteUser(@PathVariable("id") long id) {
        try {
            service.delete(id);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (AppException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
}
