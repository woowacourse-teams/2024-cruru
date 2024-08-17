package com.cruru.util.fixture;

import com.cruru.answer.domain.Answer;
import com.cruru.applicant.domain.Applicant;
import com.cruru.question.domain.Question;

public class AnswerFixture {

    public static Answer first(Question question, Applicant applicant) {
        return new Answer("응답1", question, applicant);
    }

    public static Answer second(Question question, Applicant applicant) {
        return new Answer("응답2", question, applicant);
    }

    public static Answer simple(Question question, Applicant applicant) {
        return new Answer("단답", question, applicant);
    }
}
