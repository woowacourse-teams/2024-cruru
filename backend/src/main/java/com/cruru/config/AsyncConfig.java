package com.cruru.config;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@Configuration
public class AsyncConfig implements AsyncConfigurer {

    public static final int THREAD_POOL_SIZE = 300;

    @Override
    public Executor getAsyncExecutor() {
        return Executors.newFixedThreadPool(THREAD_POOL_SIZE);
    }
    
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return AsyncConfigurer.super.getAsyncUncaughtExceptionHandler();
    }
}
