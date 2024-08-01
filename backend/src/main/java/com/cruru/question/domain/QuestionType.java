package com.cruru.question.domain;

public enum QuestionType {

    SHORT_ANSWER("SHORT_ANSWER", false),
    LONG_ANSWER("LONG_ANSWER", false),
    DROPDOWN("DROPDOWN", true),
    CHECK_BOX("CHECK_BOX", true),
    MULTIPLE_CHOICE("MULTIPLE_CHOICE", true),
    ;

    private final String type;
    private final boolean hasChoice;

    QuestionType(String type, boolean hasChoice) {
        this.type = type;
        this.hasChoice = hasChoice;
    }

    public static QuestionType fromString(String type) {
        return QuestionType.valueOf(type.toUpperCase());
    }

    public boolean hasChoice() {
        return hasChoice;
    }
}
