package com.cruru.config;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@Configuration
public class AsyncConfig implements AsyncConfigurer {

    private static final int THREAD_POOL_SIZE = 300;

    @Override
    public Executor getAsyncExecutor() {
        return Executors.newFixedThreadPool(THREAD_POOL_SIZE);
    }
}
