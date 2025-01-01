package com.cruru.member.controller.request;

import jakarta.validation.constraints.NotBlank;

public record PasswordChangeRequest(
        @NotBlank(message = "비밀번호를 입력해주세요.")
        String password
) {

}
