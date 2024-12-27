package com.cruru.email.controller.response;

import java.time.LocalDateTime;

public record EmailResponse(
        String subject,
        String content,
        LocalDateTime createdDate,
        Boolean isSucceed
) {

}
