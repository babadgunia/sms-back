package org.test.sms.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
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

    @RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("@userPermissionService.hasPermission('USER', 'DELETE')")
    public ResponseEntity<Void> delete(@PathVariable("id") long id) {
        try {
            service.delete(id);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (AppException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = "getCount", method = RequestMethod.POST)
    @PreAuthorize("@userPermissionService.hasPermission('USER', 'VIEW')")
    public ResponseEntity<Long> getCount(@RequestBody(required = false) UserFilter filter) {
        return new ResponseEntity<>(service.getCount(filter), HttpStatus.OK);
    }

    @RequestMapping(value = "getList", method = RequestMethod.POST)
    @PreAuthorize("@userPermissionService.hasPermission('USER', 'VIEW')")
    public ResponseEntity<List<User>> getList(@RequestBody(required = false) UserFilter filter) {
        return new ResponseEntity<>(service.getList(filter), HttpStatus.OK);
    }
}
