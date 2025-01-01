package com.cruru.member.controller.request;

import jakarta.validation.constraints.Email;

public record EmailChangeRequest(
        @Email
        String email
) {

}
