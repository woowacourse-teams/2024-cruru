package com.cruru.auth.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record LoginResponse(

        @JsonProperty("clubId")
        long clubId
) {

}
