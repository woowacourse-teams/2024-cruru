package com.cruru.answer.service;

import com.cruru.answer.domain.Answer;
import com.cruru.answer.domain.repository.AnswerRepository;
import com.cruru.applicant.controller.dto.QnaResponse;
import com.cruru.applicant.domain.Applicant;
import com.cruru.question.domain.Question;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerRepository answerRepository;

    public List<Answer> findAllByApplicant(Applicant applicant) {
        return answerRepository.findAllByApplicant(applicant);
    }

    public List<QnaResponse> toQnaResponses(List<Answer> answers) {
        return answers.stream()
                .map(this::toQnaResponse)
                .toList();
    }

    private QnaResponse toQnaResponse(Answer answer) {
        Question question = answer.getQuestion();
        return new QnaResponse(question.getSequence(), question.getContent(), answer.getContent());
    }
}
