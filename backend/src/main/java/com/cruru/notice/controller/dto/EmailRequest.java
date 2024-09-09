package com.cruru.notice.controller.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public record EmailRequest(

        @NotNull(message = "발신자는 필수 값입니다.")
        Long clubId,

        @NotEmpty(message = "수신자는 필수 값입니다.")
        @Valid
        List<@Email(message = "올바른 이메일 형식이어야 합니다.") String> to,

        String subject,

        String text,

        List<MultipartFile> files
) {

}
