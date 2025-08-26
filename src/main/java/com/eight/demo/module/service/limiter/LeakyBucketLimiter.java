package com.eight.demo.module.service.limiter;

import org.springframework.stereotype.Component;

import com.eight.demo.module.common.annotation.RateLimiter;
import com.eight.demo.module.common.constant.Algorithm;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class LeakyBucketLimiter implements RateLimiterStrategy {

    private final RateLimiterStorage rateLimiterStorage;

    @Override
    public boolean isAllow(String key, RateLimiter rateLimiter) {
        var capacity = rateLimiter.limit();
        var leakRate = (double) rateLimiter.limit() / rateLimiter.window();
        return rateLimiterStorage.tryAddToLeakyBucket(key, capacity, leakRate);
    }

    @Override
    public Algorithm getAlgorithmType() {
        return Algorithm.LEAKY_BUCKET;
    }

}
