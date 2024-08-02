package com.cruru.question.service;

import com.cruru.applyform.domain.ApplyForm;
import com.cruru.choice.controller.dto.ChoiceResponse;
import com.cruru.choice.service.ChoiceService;
import com.cruru.question.controller.dto.QuestionCreateRequest;
import com.cruru.question.controller.dto.QuestionResponse;
import com.cruru.question.domain.Question;
import com.cruru.question.domain.QuestionType;
import com.cruru.question.domain.repository.QuestionRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final ChoiceService choiceService;

    public List<Question> createAll(List<QuestionCreateRequest> requests, ApplyForm applyForm) {
        return requests.stream()
                .map(questionCreateRequest -> create(questionCreateRequest, applyForm))
                .toList();
    }

    public Question create(QuestionCreateRequest request, ApplyForm applyForm) {
        QuestionType questionType = QuestionType.fromString(request.type());
        Question savedQuestion = questionRepository.save(toQuestion(questionType, request, applyForm));

        if (savedQuestion.hasChoice()) {
            choiceService.createAll(request.choices(), savedQuestion.getId());
        }

        return savedQuestion;
    }

    private Question toQuestion(QuestionType type, QuestionCreateRequest request, ApplyForm applyForm) {
        return new Question(type, request.question(), request.description(), request.orderIndex(), applyForm);
    }

    public List<QuestionResponse> findByApplyFormId(Long applyFormId) {
        return questionRepository.findAllByApplyFormId(applyFormId)
                .stream()
                .map(this::toQuestionResponse)
                .toList();
    }

    private QuestionResponse toQuestionResponse(Question question) {
        List<ChoiceResponse> choiceResponses = choiceService.findAllByQuestionId(question.getId());

        return new QuestionResponse(
                question.getId(),
                question.getQuestionType().name(),
                question.getContent(),
                question.getDescription(),
                question.getSequence(),
                choiceResponses
        );
    }
}
