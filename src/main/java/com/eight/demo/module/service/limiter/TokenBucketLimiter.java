package com.eight.demo.module.service.limiter;

import org.springframework.stereotype.Component;

import com.eight.demo.module.common.annotation.RateLimiter;
import com.eight.demo.module.common.constant.Algorithm;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class TokenBucketLimiter implements RateLimiterStrategy {

    private final RateLimiterStorage storage;

    @Override
    public boolean isAllow(String key, RateLimiter rateLimiter) {
        var capacity = rateLimiter.limit();
        var refillRate = Math.max(1, rateLimiter.limit() / rateLimiter.window());
        return storage.tryConsumeToken(key, capacity, refillRate);
    }

    @Override
    public Algorithm getAlgorithmType() {
        return Algorithm.TOKEN_BUCKET;
    }

}
