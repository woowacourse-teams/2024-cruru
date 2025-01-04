package com.cruru.email.controller.response;

import java.util.List;

public record EmailHistoryResponses(
        List<EmailHistoryResponse> emailHistoryResponses
) {
}
