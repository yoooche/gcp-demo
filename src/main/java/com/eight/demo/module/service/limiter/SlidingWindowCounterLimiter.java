package com.eight.demo.module.service.limiter;

import org.springframework.stereotype.Component;

import com.eight.demo.module.common.annotation.RateLimiter;
import com.eight.demo.module.common.constant.Algorithm;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class SlidingWindowCounterLimiter implements RateLimiterStrategy {

    private final RateLimiterStorage storage;

    @Override
    public boolean isAllow(String key, RateLimiter rateLimiter) {
        var currentTimestamp = System.currentTimeMillis() / 1000;
        var currentCount = storage.addToSlidingWindow(key, currentTimestamp, rateLimiter.window());
        return currentCount <= rateLimiter.limit();
    }

    @Override
    public Algorithm getAlgorithmType() {
        return Algorithm.SLIDING_WINDOW_COUNTER;
    }

}
