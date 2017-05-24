package org.test.sms.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestApiController {

    @RequestMapping("/api/hello")
    public String greet() {
        return "Hello from the other side!!!";
    }
}