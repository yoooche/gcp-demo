package com.eight.demo.module.service.limiter;

import com.eight.demo.module.common.annotation.RateLimiter;
import com.eight.demo.module.common.constant.Algorithm;

public interface RateLimiterStrategy {

    boolean isAllow(String key, RateLimiter rateLimiter);

    Algorithm getAlgorithmType();
}
