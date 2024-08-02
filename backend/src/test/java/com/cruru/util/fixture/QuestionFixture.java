package com.cruru.util.fixture;

import static com.cruru.question.domain.QuestionType.CHECK_BOX;
import static com.cruru.question.domain.QuestionType.DROPDOWN;
import static com.cruru.question.domain.QuestionType.LONG_ANSWER;
import static com.cruru.question.domain.QuestionType.MULTIPLE_CHOICE;
import static com.cruru.question.domain.QuestionType.SHORT_ANSWER;

import com.cruru.applyform.domain.ApplyForm;
import com.cruru.question.domain.Question;
import java.util.List;

public class QuestionFixture {

    public static List<Question> createAllTypesOfQuestions(ApplyForm applyForm) {
        return List.of(
                createShortAnswerQuestion(applyForm),
                createLongAnswerQuestion(applyForm),
                createDropdownQuestion(applyForm),
                createCheckboxQuestion(applyForm),
                createMultipleChoiceQuestion(applyForm)
        );
    }

    public static Question createShortAnswerQuestion(ApplyForm applyForm) {
        return new Question(SHORT_ANSWER, "주관식 단답형", "50자 이하로 입력해주세요.", 1, applyForm);
    }

    public static Question createLongAnswerQuestion(ApplyForm applyForm) {
        return new Question(LONG_ANSWER, "주관식 장문형", "50자 이하로 입력해주세요.", 2, applyForm);
    }

    public static Question createDropdownQuestion(ApplyForm applyForm) {
        return new Question(DROPDOWN, "객관식 단일 선택", "단 하나만 골라주세요.", 3, applyForm);
    }

    public static Question createCheckboxQuestion(ApplyForm applyForm) {
        return new Question(CHECK_BOX, "체크 선택", "", 4, applyForm);
    }

    public static Question createMultipleChoiceQuestion(ApplyForm applyForm) {
        return new Question(MULTIPLE_CHOICE, "객관식 다중 선택", "모두 선택해주세요.", 5, applyForm);
    }
}
