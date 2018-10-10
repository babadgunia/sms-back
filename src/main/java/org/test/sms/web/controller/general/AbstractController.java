package org.test.sms.web.controller.general;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.test.sms.common.exception.AppException;

@Component
public abstract class AbstractController {

    @Autowired
    protected ModelMapper modelMapper;

    protected <T> ResponseEntity<T> getErrorResponse(AppException e) {
        switch (e.getErrorCode()) {
            case ALREADY_MODIFIED: {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
            case NOT_FOUND: {
                return ResponseEntity.notFound().build();
            }
            default: {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
    }
}