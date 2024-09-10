package com.cruru.member.controller.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record MemberCreateRequest(
        @NotBlank(message = "단체명을 입력해주세요.")
        String clubName,

        @NotBlank(message = "이메일을 입력해주세요.")
        @Email(message = "이메일의 형식이 올바르지 않습니다.")
        String email,

        @NotBlank(message = "비밀번호를 입력해주세요.")
        String password,

        @NotBlank(message = "전화번호를 입력해주세요.")
        String phone
) {

}
