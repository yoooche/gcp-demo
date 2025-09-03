package com.eight.demo.module.constant;

import lombok.Getter;

@Getter
public enum RedisKey {

    RATE_LIMITER("rate-limiter");

    private String value;

    RedisKey(String value) {
        this.value = value;
    }
}
