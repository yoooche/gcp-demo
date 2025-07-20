package com.eight.demo.module.service.limiter;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Component;

@Component
public class MemoryRateLimiterStorage implements RateLimiterStorage {

    private static final ConcurrentHashMap<String, Counter> storage = new ConcurrentHashMap<>();
    private static final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    public MemoryRateLimiterStorage() {
        scheduler.scheduleAtFixedRate(this::cleanExpireKey, 30, 30, TimeUnit.SECONDS);
    }

    @Override
    public long incrementAndSetExpire(String key, long expireSeconds) {
        var expireTime = System.currentTimeMillis() + expireSeconds  * 1000;
        var counter = storage.compute(key, (k, existing) -> {
            if (existing == null || existing.isExpired()) {
                return new Counter(new AtomicLong(1), expireTime);
            } else {
                existing.count.incrementAndGet();
                return existing;
            }
        });
        return counter.count.longValue();
    }

    private void cleanExpireKey() {
        storage.entrySet().removeIf(entry -> entry.getValue().isExpired());
    }

    private record Counter(AtomicLong count, long expireTime) {
        public boolean isExpired() {
            return System.currentTimeMillis() > expireTime;
        }
    }
}
