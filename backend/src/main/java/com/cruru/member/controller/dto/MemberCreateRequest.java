package com.cruru.member.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record MemberCreateRequest(
        @NotBlank(message = "이메일은 필수 값입니다.")
        @Email(message = "이메일의 형식이 올바르지 않습니다.")
        String email,

        @NotBlank(message = "비밀번호는 필수 값입니다.")
        String password,

        @NotBlank(message = "전화번호는 필수 값입니다.")
        String phone
) {

}
