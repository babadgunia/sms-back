package org.test.sms.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.test.sms.common.enums.general.Translations;

import java.util.Map;

@RestController
@RequestMapping(value = "api/translation")
public class TranslationController {

    @RequestMapping(value = "get", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Map<String, String>>> get() {
        return new ResponseEntity<>(Translations.getTranslations(), HttpStatus.OK);
    }
}