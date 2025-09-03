package com.eight.demo.module.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.eight.demo.module.common.error.BaseException;
import com.eight.demo.module.constant.StatusCode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaSender {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void send(String topic, Object body) {
        try {
            var om = new ObjectMapper();
            var message = om.writeValueAsString(body);
            kafkaTemplate.send(topic, message);
        } catch (JsonProcessingException e) {
            throw new BaseException(StatusCode.UNKNOW_ERR, "Json parse failed");
        }
    }
}
