package com.eight.demo.module.utils;

import com.eight.demo.module.common.annotation.RateLimiter;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Component;

@Component
public class RateLimiterKeyGenerator {

    private static final String KEY_SEPARATOR = ":";

    public String generateKey(ProceedingJoinPoint joinPoint, RateLimiter rateLimiter) {
        var className = joinPoint.getTarget().getClass().getSimpleName();
        var methodName = joinPoint.getSignature().getName();
        var customKey = rateLimiter.key();
        var windowStartTime = getWindowStartTime(rateLimiter.window());
        return String.join(KEY_SEPARATOR, className, methodName, customKey, String.valueOf(windowStartTime));
    }

    private long getWindowStartTime(int windowSeconds) {
        var currentTimeSeconds = System.currentTimeMillis() / 1000;
        return (currentTimeSeconds / windowSeconds) * windowSeconds;
    }
}
