package com.cruru.auth.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record LoginResponse(

        @JsonProperty("clubId")
        long id
) {

}
