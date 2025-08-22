package com.eight.demo.module.service.limiter;

public interface RateLimiterStorage {

    long incrementAndSetExpire(String key, long expireSeconds);

    long addToSlidingWindow(String key, long timestamp, int windowSeconds);

    long getCountInSlidingWindow(String key, long windownStartTime, long windowEndTime);

    boolean tryConsumeToken(String key, int capacity, int refillRate);

    void cleanupExpiredWindows(String key, long expireTime);
}
