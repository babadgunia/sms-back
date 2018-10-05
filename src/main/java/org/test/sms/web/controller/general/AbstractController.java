package org.test.sms.web.controller.general;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public abstract class AbstractController {

    @Autowired
    protected ModelMapper modelMapper;
}