package org.test.sms.web.controller.general;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.test.sms.common.entity.general.UserGroup;
import org.test.sms.common.exception.AppException;
import org.test.sms.common.filter.general.UserGroupFilter;
import org.test.sms.common.service.general.UserGroupService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "api/userGroup")
public class UserGroupController {

    private UserGroupService service;

    @Autowired
    public UserGroupController(UserGroupService service) {
        this.service = service;
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    @PreAuthorize("@userService.hasPermission('USER_GROUP', 'ADD')")
    public ResponseEntity<UserGroup> add(@RequestBody UserGroup entity) {
        try {
            return new ResponseEntity<>(service.add(entity), HttpStatus.OK);
        } catch (AppException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    @PreAuthorize("@userService.hasPermission('USER_GROUP', 'EDIT')")
    public ResponseEntity<UserGroup> update(@RequestBody UserGroup entity) {
        try {
            return new ResponseEntity<>(service.update(entity), HttpStatus.OK);
        } catch (AppException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("@userService.hasPermission('USER_GROUP', 'DELETE')")
    public ResponseEntity<Void> delete(@PathVariable("id") long id) {
        try {
            service.delete(id);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (AppException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = "get/{id}", method = RequestMethod.GET)
    @PreAuthorize("@userService.hasPermission('USER_GROUP', 'VIEW')")
    public ResponseEntity<UserGroup> get(@PathVariable("id") long id) {
        Optional<UserGroup> entityWrapper = service.get(id);

        return new ResponseEntity<>(entityWrapper.orElse(null), HttpStatus.OK);
    }

    @RequestMapping(value = "getCount", method = RequestMethod.POST)
    @PreAuthorize("@userService.hasPermission('USER_GROUP', 'VIEW')")
    public ResponseEntity<Long> getCount(@RequestBody(required = false) UserGroupFilter filter) {
        return new ResponseEntity<>(service.getCount(filter), HttpStatus.OK);
    }

    @RequestMapping(value = "getList", method = RequestMethod.POST)
    @PreAuthorize("@userService.hasPermission('USER_GROUP', 'VIEW')")
    public ResponseEntity<List<UserGroup>> getList(@RequestBody(required = false) UserGroupFilter filter) {
        return new ResponseEntity<>(service.getList(filter), HttpStatus.OK);
    }

//    misc

    @RequestMapping(value = "getListForSelection", method = RequestMethod.GET)
    @PreAuthorize("@userService.hasPermission('USER_GROUP', 'VIEW')")
    public ResponseEntity<List<UserGroup>> getListForSelection() {
        return new ResponseEntity<>(service.getListForSelection(), HttpStatus.OK);
    }
}