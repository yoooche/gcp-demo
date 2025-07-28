package com.eight.demo.module.service.limiter;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Component;

@Component
public class MemoryRateLimiterStorage implements RateLimiterStorage {

    private static final ConcurrentHashMap<String, Counter> storage = new ConcurrentHashMap<>();
    private static final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private static final ConcurrentHashMap<String, ConcurrentSkipListMap<Long, AtomicLong>> slidingWindowStorage = new ConcurrentHashMap<>();
    private static final int SLIDING_WIN_BUCKET_SIZE_SECS = 10;

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

    @Override
    public long addToSlidingWindow(String key, long timestamp, int windowSeconds) {
        var bucketTimestamp = (timestamp / SLIDING_WIN_BUCKET_SIZE_SECS) * SLIDING_WIN_BUCKET_SIZE_SECS;
        var timeSeries = slidingWindowStorage.computeIfAbsent(key, k -> new ConcurrentSkipListMap<>());
        var bucketCount = timeSeries.computeIfAbsent(bucketTimestamp, k -> new AtomicLong(0));

        bucketCount.incrementAndGet();

        var expireTime = timestamp - windowSeconds;
        timeSeries.headMap(expireTime).clear();

        return getCountInSlidingWindow(key, timestamp - windowSeconds, timestamp);
    }

    @Override
    public long getCountInSlidingWindow(String key, long windownStartTime, long windowEndTime) {
        var timeSeries = slidingWindowStorage.get(key);
        if (timeSeries == null) {
            return 0;
        }

        return timeSeries.subMap(windownStartTime, true, windowEndTime, true)
                        .values()
                        .stream()
                        .mapToLong(AtomicLong::get)
                        .sum();
    }

    @Override
    public void cleanupExpiredWindows(String key, long expireTime) {
        var timeSeries = slidingWindowStorage.get(key);
        if (timeSeries != null) {
            timeSeries.headMap(expireTime).clear();
            if (timeSeries.isEmpty()) {
                slidingWindowStorage.remove(key);
            }
        }
    }

    private void cleanExpireKey() {
        var currentTime = System.currentTimeMillis() / 1000;
        var expireTime = currentTime - 1000;
        
        // fixed window
        storage.entrySet().removeIf(entry -> entry.getValue().isExpired());

        // sliding window
        slidingWindowStorage.forEach((key, timeSeries) -> {
            timeSeries.headMap(expireTime).clear();
        });
        slidingWindowStorage.entrySet().removeIf((entry -> entry.getValue().isEmpty()));
    }

    private record Counter(AtomicLong count, long expireTime) {
        public boolean isExpired() {
            return System.currentTimeMillis() > expireTime;
        }
    }
}
