package com.cruru.util.fixture;

import com.cruru.choice.domain.Choice;
import com.cruru.question.domain.Question;
import java.util.List;

public class ChoiceFixture {

    public static List<Choice> fiveChoices(Question question) {
        return List.of(
                first(question),
                second(question),
                third(question),
                fourth(question),
                fifth(question)
        );
    }

    public static Choice first(Question question) {
        return new Choice("1번 선택지", 1, question);
    }

    public static Choice second(Question question) {
        return new Choice("2번 선택지", 2, question);
    }

    public static Choice third(Question question) {
        return new Choice("3번 선택지", 3, question);
    }

    public static Choice fourth(Question question) {
        return new Choice("4번 선택지", 4, question);
    }

    public static Choice fifth(Question question) {
        return new Choice("5번 선택지", 5, question);
    }
}
