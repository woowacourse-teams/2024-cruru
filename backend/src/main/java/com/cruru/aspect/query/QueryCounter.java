package com.cruru.aspect.query;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Getter
@Component
@RequestScope
public class QueryCounter {

    private static final int WARN_QUERY_COUNT = 1;

    private int count;
    private List<String> queries = new ArrayList<>();

    public void addQuery(String query) {
        queries.add(query);
        increaseCount();
    }

    private void increaseCount() {
        count++;
    }

    public boolean isWarn() {
        return count >= WARN_QUERY_COUNT;
    }
}
