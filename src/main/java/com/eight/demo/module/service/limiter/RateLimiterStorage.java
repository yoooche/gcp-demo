package com.eight.demo.module.service.limiter;

public interface RateLimiterStorage {

    long incrementAndSetExpire(String key, long expireSeconds);

    long addToSlidingWindow(String key, long timestamp, int windowSeconds);

    long getCountInSlidingWindow(String key, long windownStartTime, long windowEndTime);

    void cleanupExpiredWindows(String key, long expireTime);
}
