package com.eight.demo.module.service.limiter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class FixedWindowLimiter {

    private final RateLimiterStorage storage;

    public boolean isAllowed(String key, int limit, int windowSeconds) {
        var currentCount = storage.incrementAndSetExpire(key, windowSeconds);
        return currentCount <= limit;
    }
}
