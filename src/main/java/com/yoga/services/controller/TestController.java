package com.yoga.services.controller;

import com.yoga.services.service.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class TestController {
    private final Logger LOG = LoggerFactory.getLogger(TestController.class);
    private TestService testService;

    public TestController(TestService testService) {
        this.testService = testService;
    }

  /*  @RequestMapping(value="/swapi", method= RequestMethod.GET)
    public ResponseEntity<Map<String,String>> swapi(){
        LOG.info("in swapi calling service");
        Map map = testService.swapi();
        return new ResponseEntity<Map<String,String>>(map, HttpStatus.OK);
    }*/
}
