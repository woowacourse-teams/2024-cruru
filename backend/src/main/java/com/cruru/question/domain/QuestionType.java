package com.cruru.question.domain;

public enum QuestionType {

    SHORT_ANSWER("SHORT_ANSWER"),
    LONG_ANSWER("LONG_ANSWER"),
    DROPDOWN("DROPDOWN"),
    CHECK_BOX("CHECK_BOX"),
    MULTIPLE_CHOICE("MULTIPLE_CHOICE"),
    ;

    private String type;

    QuestionType(String type) {
        this.type = type;
    }

    public static QuestionType fromString(String type) {
        return QuestionType.valueOf(type.toUpperCase());
    }
}
