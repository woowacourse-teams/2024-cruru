package com.cruru.club.controller.dto;

import jakarta.validation.constraints.NotBlank;

public record ClubCreateRequest(@NotBlank String name) {

}
