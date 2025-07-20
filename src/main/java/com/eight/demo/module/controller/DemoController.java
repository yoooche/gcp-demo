package com.eight.demo.module.controller;

import com.eight.demo.module.common.annotation.RateLimiter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/demo")
@RestController
public class DemoController {

    @RateLimiter(key = "demo", limit = 5)
    @GetMapping
    public ResponseEntity<String> getDemoString() {
        return ResponseEntity.ok("Rate limiter test");
    }
}
