package com.eight.demo.module.service.limiter;

public interface RateLimiterStorage {

    long incrementAndSetExpire(String key, long expireSeconds);

}
