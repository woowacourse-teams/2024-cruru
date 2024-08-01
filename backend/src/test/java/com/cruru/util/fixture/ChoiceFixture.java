package com.cruru.util.fixture;

import com.cruru.choice.domain.Choice;
import com.cruru.question.domain.Question;
import java.util.List;

public class ChoiceFixture {

    public static Choice createFirstChoice(Question question) {
        return new Choice("1번 선택지", 1, question);
    }

    public static Choice createSecondChoice(Question question) {
        return new Choice("2번 선택지", 2, question);
    }

    public static Choice createThirdChoice(Question question) {
        return new Choice("3번 선택지", 3, question);
    }

    public static Choice createFourthChoice(Question question) {
        return new Choice("4번 선택지", 4, question);
    }

    public static Choice createFifthChoice(Question question) {
        return new Choice("5번 선택지", 5, question);
    }

    public static List<Choice> createChoices(Question question) {
        return List.of(
                createFirstChoice(question),
                createSecondChoice(question),
                createThirdChoice(question),
                createFourthChoice(question),
                createFifthChoice(question)
        );
    }
}
