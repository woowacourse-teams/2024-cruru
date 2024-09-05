package com.cruru.aspect.query;

import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Getter
@Component
@RequestScope
public class QueryCounter {

    private static final int WARN_QUERY_COUNT = 1;

    private int count;

    public void increaseCount() {
        count++;
    }

    public boolean isWarn() {
        return count >= WARN_QUERY_COUNT;
    }
}
