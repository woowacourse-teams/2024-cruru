package com.cruru.question.service;

import com.cruru.applyform.domain.ApplyForm;
import com.cruru.choice.controller.dto.ChoiceResponse;
import com.cruru.choice.domain.Choice;
import com.cruru.choice.service.ChoiceService;
import com.cruru.question.controller.dto.QuestionCreateRequest;
import com.cruru.question.controller.dto.QuestionResponse;
import com.cruru.question.domain.Question;
import com.cruru.question.domain.QuestionType;
import com.cruru.question.domain.repository.QuestionRepository;
import java.util.ArrayList;
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

    @Transactional
    public void createAll(List<QuestionCreateRequest> requests, ApplyForm applyForm) {
        requests.forEach(questionCreateRequest -> create(questionCreateRequest, applyForm));
    }

    @Transactional
    public Question create(QuestionCreateRequest request, ApplyForm applyForm) {
        QuestionType questionType = QuestionType.valueOf(request.type());
        Question savedQuestion = questionRepository.save(toQuestion(questionType, request, applyForm));

        if (savedQuestion.hasChoice()) {
            choiceService.createAll(request.choices(), savedQuestion.getId());
        }

        return savedQuestion;
    }

    private Question toQuestion(QuestionType type, QuestionCreateRequest request, ApplyForm applyForm) {
        return new Question(
                type,
                request.question(),
                request.description(),
                request.orderIndex(),
                request.required(),
                applyForm
        );
    }

    public List<Question> findByApplyFormId(Long applyFormId) {
        return questionRepository.findAllByApplyFormId(applyFormId);
    }

    public List<QuestionResponse> toQuestionResponses(List<Question> questions) {
        return questions.stream()
                .map(this::toQuestionResponse)
                .toList();
    }

    private QuestionResponse toQuestionResponse(Question question) {
        return new QuestionResponse(
                question.getId(),
                question.getQuestionType().name(),
                question.getContent(),
                question.getDescription(),
                question.getSequence(),
                getChoiceResponses(question),
                question.getRequired()
        );
    }

    private List<ChoiceResponse> getChoiceResponses(Question question) {
        List<ChoiceResponse> choiceResponses = new ArrayList<>();
        if (question.hasChoice()) {
            List<Choice> choices = choiceService.findAllByQuestionId(question.getId());
            return choiceService.toChoiceResponses(choices);
        }
        return choiceResponses;
    }
}
