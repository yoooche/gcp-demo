package com.eight.demo.module.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/demo")
@RestController
public class DemoController {

    @GetMapping
    public ResponseEntity<String> getDemoString() {
        return ResponseEntity.ok("Demo rebase test");
    }

}
