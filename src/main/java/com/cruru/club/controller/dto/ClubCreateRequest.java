package com.cruru.club.controller.dto;

import jakarta.validation.constraints.NotBlank;

public record ClubCreateRequest(
        @NotBlank(message = "동아리 이름은 필수 값입니다.")
        String name
) {

}
