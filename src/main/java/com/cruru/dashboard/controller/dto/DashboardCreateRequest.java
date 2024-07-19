package com.cruru.dashboard.controller.dto;

import jakarta.validation.constraints.NotBlank;

public record DashboardCreateRequest(
        @NotBlank(message = "대시보드 이름은 필수 값입니다.")
        String name
) {

}
