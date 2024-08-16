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
        return new Question(SHORT_ANSWER, "주관식 단답형", "50자 이하로 입력해주세요.", 1, false, applyForm);
    }

    public static Question longAnswerType(ApplyForm applyForm) {
        return new Question(LONG_ANSWER, "주관식 장문형", "50자 이하로 입력해주세요.", 2, false, applyForm);
    }

    public static Question dropdownType(ApplyForm applyForm) {
        return new Question(DROPDOWN, "객관식 단일 선택", "단 하나만 골라주세요.", 3, false, applyForm);
    }

    public static Question multipleChoiceType(ApplyForm applyForm) {
        return new Question(MULTIPLE_CHOICE, "객관식 다중 선택", "모두 선택해주세요.", 4, false, applyForm);
    }

    public static Question singleChoiceType(ApplyForm applyForm) {
        return new Question(SINGLE_CHOICE, "객관식 단일 선택", "선택해주세요.", 5, false, applyForm);
    }

    public static List<Question> nonChoiceType(ApplyForm applyForm) {
        return List.of(
                shortAnswerType(applyForm),
                longAnswerType(applyForm)
        );
    }
}
