package com.cruru.process.domain;

import lombok.Getter;

@Getter
public enum ProcessType {
    APPLY(false),
    EVALUATE(true),
    APPROVE(false),
    ;

    private final boolean modifiable;

    ProcessType(boolean modifiable) {
        this.modifiable = modifiable;
    }
}
