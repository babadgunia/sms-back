package org.test.sms.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.test.sms.common.entity.general.Hero;
import org.test.sms.common.exception.AppException;
import org.test.sms.common.service.general.HeroService;

import java.util.List;

@RestController
@RequestMapping("api/hero")
public class HeroController {

    private HeroService service;

    @Autowired
    public HeroController(HeroService service) {
        this.service = service;
    }

    @RequestMapping("hello")
    public String greet() {
        return "Hello from the other side!!!";
    }

    @RequestMapping(value = "getList")
    public List<Hero> getList() throws AppException {
        return service.getList(null);
    }
}