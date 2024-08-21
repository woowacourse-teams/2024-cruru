package com.cruru.util.fixture;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class LocalDateFixture {

    public static Clock fixedClock() {
        return Clock.fixed(Instant.parse("2024-08-01T00:00:00Z"), ZoneOffset.UTC);
    }

    public static LocalDateTime startDate() {
        return LocalDateTime.of(2024, 8, 2, 10, 0, 0);
    }

    public static LocalDateTime endDate() {
        return LocalDateTime.of(2024, 8, 9, 17, 0, 0);
    }

    public static LocalDateTime oneWeekAgo() {
        return LocalDateTime.of(2024, 7, 25, 10, 0, 0);
    }
}
