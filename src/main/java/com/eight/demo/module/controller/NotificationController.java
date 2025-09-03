package com.eight.demo.module.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping(value = "/notification")
@RestController
public class NotificationController {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @GetMapping(value = "/kafka-test")
    public ResponseEntity<String> kafkaTest() {
        kafkaTemplate.send("TEST", "kafka 666");
        return ResponseEntity.ok("kafka-test");
    }
}
