package com.eight.demo.module.controller;

import com.eight.demo.module.common.constant.StatusCode;
import com.eight.demo.module.common.error.BaseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/demo")
@RestController
public class DemoController {

    @GetMapping
    public ResponseEntity<String> getDemoString() {
        throw new BaseException(StatusCode.TOO_MANY_REQUEST, "TEST");
    }

}
