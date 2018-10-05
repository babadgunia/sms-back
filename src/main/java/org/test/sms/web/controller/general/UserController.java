package org.test.sms.web.controller.general;

import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.test.sms.common.entity.general.User;
import org.test.sms.common.exception.AppException;
import org.test.sms.common.filter.general.UserFilter;
import org.test.sms.common.service.general.UserService;
import org.test.sms.web.dto.general.UserDto;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/user")
public class UserController extends AbstractController {

    private UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("add")
    @PreAuthorize("@userService.hasPermission('USER', 'ADD')")
    public ResponseEntity<UserDto> add(@RequestBody @Valid UserDto entityDto) {
        try {
            return ResponseEntity.ok(modelMapper.map(service.add(modelMapper.map(entityDto, User.class)), UserDto.class));
        } catch (AppException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PostMapping("update")
    @PreAuthorize("@userService.hasPermission('USER', 'EDIT')")
    public ResponseEntity<UserDto> update(@RequestBody @Valid UserDto entityDto) {
        try {
            return ResponseEntity.ok(modelMapper.map(service.update(modelMapper.map(entityDto, User.class)), UserDto.class));
        } catch (AppException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("delete/{id}")
    @PreAuthorize("@userService.hasPermission('USER', 'DELETE')")
    public ResponseEntity<Void> delete(@PathVariable("id") long id) {
        try {
            service.delete(id);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (AppException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @GetMapping("get/{id}")
    @PreAuthorize("@userService.hasPermission('USER', 'VIEW')")
    public ResponseEntity<UserDto> get(@PathVariable("id") long id) {
        Optional<User> entityWrapper = service.get(id);

        return ResponseEntity.ok(modelMapper.map(entityWrapper.orElse(null), UserDto.class));
    }

    @PostMapping("getCount")
    @PreAuthorize("@userService.hasPermission('USER', 'VIEW')")
    public ResponseEntity<Long> getCount(@RequestBody(required = false) UserFilter filter) {
        return ResponseEntity.ok(service.getCount(filter));
    }

    @PostMapping("getList")
    @PreAuthorize("@userService.hasPermission('USER', 'VIEW')")
    public ResponseEntity<List<UserDto>> getList(@RequestBody(required = false) UserFilter filter) {
        List<UserDto> result = modelMapper.map(service.getList(filter), new TypeToken<List<UserDto>>() {}.getType());

        return ResponseEntity.ok(result);
    }
}