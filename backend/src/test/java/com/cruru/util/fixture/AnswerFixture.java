package com.cruru.util.fixture;

import com.cruru.answer.domain.Answer;
import com.cruru.applicant.domain.Applicant;
import com.cruru.question.domain.Question;

public class AnswerFixture {

    public static Answer fristAnswer(Question question, Applicant applicant) {
        return new Answer("응답1", question, applicant);
    }

    public static Answer secondAnswer(Question question, Applicant applicant) {
        return new Answer("응답2", question, applicant);
    }
}
