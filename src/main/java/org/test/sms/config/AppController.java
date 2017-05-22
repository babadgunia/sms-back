package org.test.sms.config;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class AppController {

    @RequestMapping("/")
    public String getIndexPage() {
        return "index";
    }
}