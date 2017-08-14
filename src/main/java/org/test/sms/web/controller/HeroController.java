package org.test.sms.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import org.test.sms.common.entity.general.Hero;
import org.test.sms.common.exception.AppException;
import org.test.sms.common.filter.general.HeroFilter;
import org.test.sms.common.service.general.HeroService;
import org.test.sms.common.utils.Utils;

import java.util.List;

@RestController
@RequestMapping(value = "api/hero")
public class HeroController {

    private HeroService service;

    @Autowired
    public HeroController(HeroService service) {
        this.service = service;
    }

    @RequestMapping(value = "getList", method = RequestMethod.GET)
    public ResponseEntity<List<Hero>> getList(@RequestParam String name) {
        HeroFilter filter = new HeroFilter();

        if (!Utils.isBlank(name)) {
            filter.setName(name);
        }

        return new ResponseEntity<>(service.getList(filter), HttpStatus.OK);
    }

    @RequestMapping(value = "get/{id}", method = RequestMethod.GET)
    public ResponseEntity<Hero> get(@PathVariable("id") long id) {
        return service.get(id).map(hero -> new ResponseEntity<>(hero, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public ResponseEntity<Hero> createUser(@RequestBody Hero hero, UriComponentsBuilder ucBuilder) {
        try {
            return new ResponseEntity<>(service.add(hero), HttpStatus.CREATED);
        } catch (AppException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = "update", method = RequestMethod.PUT)
    public ResponseEntity<Hero> update(@RequestBody Hero hero) {
        try {
            return new ResponseEntity<>(service.update(hero), HttpStatus.OK);
        } catch (AppException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
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