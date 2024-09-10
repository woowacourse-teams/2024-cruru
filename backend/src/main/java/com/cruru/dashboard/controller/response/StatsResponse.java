package com.cruru.dashboard.controller.response;

public record StatsResponse(
        int accept,

        int fail,

        int inProgress,

        int total
) {

}
