package com.cruru.util.fixture;

import static com.cruru.question.domain.QuestionType.DROPDOWN;
import static com.cruru.question.domain.QuestionType.LONG_ANSWER;
import static com.cruru.question.domain.QuestionType.MULTIPLE_CHOICE;
import static com.cruru.question.domain.QuestionType.SHORT_ANSWER;
import static com.cruru.question.domain.QuestionType.SINGLE_CHOICE;

import com.cruru.applyform.domain.ApplyForm;
import com.cruru.question.domain.Question;
import java.util.List;

public class QuestionFixture {

    public static List<Question> allTypes(ApplyForm applyForm) {
        return List.of(
                shortAnswerType(applyForm),
                longAnswerType(applyForm),
                dropdownType(applyForm),
                multipleChoiceType(applyForm),
                singleChoiceType(applyForm)
        );
    }

    public static Question shortAnswerType(ApplyForm applyForm) {
        return new Question(
                SHORT_ANSWER,
                "주관식 단답형",
                "한 줄로 답변이 가능한 짧은 문장을 작성해주세요.",
                1, false,
                applyForm
        );
    }

    public static Question longAnswerType(ApplyForm applyForm) {
        return new Question(
                LONG_ANSWER,
                "주관식 장문형",
                "장문으로 서술이 필요한 질문에 대한 자세한 답변을 작성해주세요.",
                2,
                false,
                applyForm
        );
    }

    public static Question dropdownType(ApplyForm applyForm) {
        return new Question(
                DROPDOWN,
                "객관식 단일 선택",
                "주어진 목록에서 하나의 옵션을 선택해주세요.",
                3,
                false,
                applyForm
        );
    }

    public static Question multipleChoiceType(ApplyForm applyForm) {
        return new Question(
                MULTIPLE_CHOICE,
                "객관식 다중 선택",
                "주어진 목록에서 여러 개의 옵션을 선택해주세요.",
                4,
                false,
                applyForm
        );
    }

    public static Question singleChoiceType(ApplyForm applyForm) {
        return new Question(
                SINGLE_CHOICE,
                "객관식 단일 선택",
                "주어진 목록에서 하나의 옵션을 선택해주세요.",
                5,
                false,
                applyForm
        );
    }

    public static List<Question> nonChoiceType(ApplyForm applyForm) {
        return List.of(
                shortAnswerType(applyForm),
                longAnswerType(applyForm)
        );
    }

    public static Question required(ApplyForm applyForm) {
        return new Question(
                SHORT_ANSWER,
                "주관식 단답형",
                "필수 입력 항목입니다. 간단하게 답변을 작성해주세요.",
                1,
                true,
                applyForm
        );
    }
}
