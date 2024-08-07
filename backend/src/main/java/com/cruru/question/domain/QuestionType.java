package com.cruru.question.domain;

public enum QuestionType {

    SHORT_ANSWER(false),
    LONG_ANSWER(false),
    DROPDOWN(true),
    CHECK_BOX(true),
    MULTIPLE_CHOICE(true),
    ;

    private final boolean hasChoice;

    QuestionType(boolean hasChoice) {
        this.hasChoice = hasChoice;
    }

    public boolean hasChoice() {
        return hasChoice;
    }
}
