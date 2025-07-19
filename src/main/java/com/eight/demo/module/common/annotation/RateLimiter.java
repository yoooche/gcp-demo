package com.eight.demo.module.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RateLimiter {

    Algorithm algorithm() default Algorithm.FIXED_WINDOW;

    String key();

    int limit() default 10;

    int window() default 60;

    String fallback() default "";

    Class<?> fallbackClass() default Void.class;

    enum Algorithm {
        FIXED_WINDOW,
        TOKEN_BUCKET,
        SLIDING_WINDOW_LOG,
        SLIDING_WINDOW_COUNTER,
        LEAKY_BUCKET
    }
}
