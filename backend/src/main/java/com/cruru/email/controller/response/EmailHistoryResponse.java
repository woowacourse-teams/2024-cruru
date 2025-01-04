package com.cruru.email.controller.response;

import java.time.LocalDateTime;

public record EmailHistoryResponse(
        String subject,
        String content,
        LocalDateTime createdDate,
        Boolean isSucceed
) {

}
