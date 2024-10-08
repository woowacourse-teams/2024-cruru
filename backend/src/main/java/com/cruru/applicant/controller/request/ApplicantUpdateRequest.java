package com.cruru.applicant.controller.request;

import jakarta.validation.constraints.NotBlank;

public record ApplicantUpdateRequest(
        @NotBlank(message = "이름은 필수 값입니다.")
        String name,

        @NotBlank(message = "이메일은 필수 값입니다.")
        String email,

        @NotBlank(message = "전화번호는 필수 값입니다.")
        String phone
) {

}
