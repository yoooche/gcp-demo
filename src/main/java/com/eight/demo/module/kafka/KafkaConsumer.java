package com.eight.demo.module.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    @KafkaListener(topics = "TEST")
    public void process(String content) {
        System.out.println(content + " gogogo");
    }
}
