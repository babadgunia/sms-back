package org.test.sms.web.controller;

import org.modelmapper.TypeToken;
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
import org.test.sms.web.dto.general.UserDto;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "api/user")
public class UserController extends AbstractController {

    private UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    @PreAuthorize("@userService.hasPermission('USER', 'ADD')")
    public ResponseEntity<UserDto> add(@RequestBody UserDto entityDto) {
        try {
            return new ResponseEntity<>(modelMapper.map(service.add(modelMapper.map(entityDto, User.class)), UserDto.class), HttpStatus.OK);
        } catch (AppException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    @PreAuthorize("@userService.hasPermission('USER', 'EDIT')")
    public ResponseEntity<UserDto> update(@RequestBody UserDto entityDto) {
        try {
            return new ResponseEntity<>(modelMapper.map(service.update(modelMapper.map(entityDto, User.class)), UserDto.class), HttpStatus.OK);
        } catch (AppException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("@userService.hasPermission('USER', 'DELETE')")
    public ResponseEntity<Void> delete(@PathVariable("id") long id) {
        try {
            service.delete(id);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (AppException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = "get/{id}", method = RequestMethod.GET)
    @PreAuthorize("@userService.hasPermission('USER', 'VIEW')")
    public ResponseEntity<UserDto> get(@PathVariable("id") long id) {
        Optional<User> entityWrapper = service.get(id);

        return new ResponseEntity<>(modelMapper.map(entityWrapper.orElse(null), UserDto.class), HttpStatus.OK);
    }

    @RequestMapping(value = "getCount", method = RequestMethod.POST)
    @PreAuthorize("@userService.hasPermission('USER', 'VIEW')")
    public ResponseEntity<Long> getCount(@RequestBody(required = false) UserFilter filter) {
        return new ResponseEntity<>(service.getCount(filter), HttpStatus.OK);
    }

    @RequestMapping(value = "getList", method = RequestMethod.POST)
    @PreAuthorize("@userService.hasPermission('USER', 'VIEW')")
    public ResponseEntity<List<UserDto>> getList(@RequestBody(required = false) UserFilter filter) {
        List<UserDto> result = modelMapper.map(service.getList(filter), new TypeToken<List<UserDto>>() {}.getType());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}