package com.cruru.member.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record MemberCreateResponse(
        @JsonProperty(value = "member_id")
        Long id
) {

}
