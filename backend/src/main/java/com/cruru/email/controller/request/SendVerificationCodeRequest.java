package com.cruru.email.controller.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record SendVerificationCodeRequest(
        @NotBlank
        @Email
        String email
) {

}
