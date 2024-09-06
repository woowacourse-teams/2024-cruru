package com.cruru.aspect.query;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.IntStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("쿼리 카운트 테스트")
class QueryCounterTest {

    @DisplayName("쿼리 개수가 10개 넘을 경우, 경고로 인지한다.")
    @Test
    void warn() {
        // given
        int queryCnt = 10;
        QueryCounter queryCounter = new QueryCounter();

        // when
//        IntStream.range(0, queryCnt)
//                .forEach(i -> queryCounter.increaseCount());

        // then
        assertThat(queryCounter.isWarn()).isTrue();
    }
}
