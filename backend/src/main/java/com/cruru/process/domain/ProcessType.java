package com.cruru.process.domain;

import lombok.Getter;

@Getter
public enum ProcessType {
    APPLY(true),
    EVALUATE(false),
    APPROVE(true),
    ;

    private final boolean fixed;

    ProcessType(boolean fixed) {
        this.fixed = fixed;
    }
}
