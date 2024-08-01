package com.cruru.question.service;

import com.cruru.applyform.domain.ApplyForm;
import com.cruru.choice.service.ChoiceService;
import com.cruru.question.controller.dto.QuestionCreateRequest;
import com.cruru.question.domain.Question;
import com.cruru.question.domain.QuestionType;
import com.cruru.question.domain.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final ChoiceService choiceService;

    public long create(QuestionCreateRequest request, ApplyForm applyForm) {
        QuestionType questionType = QuestionType.fromString(request.type());
        Question savedQuestion = questionRepository.save(toQuestion(questionType, request, applyForm));

        if (questionType.hasChoice()) {
            choiceService.createAll(request.choices(), savedQuestion);
        }

        return savedQuestion.getId();
    }

    private Question toQuestion(QuestionType type, QuestionCreateRequest request, ApplyForm applyForm) {
        return new Question(type, request.question(), request.orderIndex(), applyForm);
    }
}
