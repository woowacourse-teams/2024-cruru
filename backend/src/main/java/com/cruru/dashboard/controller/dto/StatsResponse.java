package com.cruru.dashboard.controller.dto;

public record StatsResponse(
        int accept,

        int fail,

        int inProgress,

        int total
) {

}
