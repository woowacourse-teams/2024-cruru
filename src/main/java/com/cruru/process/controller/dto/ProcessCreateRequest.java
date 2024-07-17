package com.cruru.process.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ProcessCreateRequest(
        @JsonProperty("process_name")
        String name,
        
        String description,

        @JsonProperty("order_index")
        int sequence
) {

}
