package com.cruru.question.service;

import com.cruru.applyform.domain.ApplyForm;
import com.cruru.question.controller.request.QuestionCreateRequest;
import com.cruru.question.controller.response.ChoiceResponse;
import com.cruru.question.controller.response.QuestionResponse;
import com.cruru.question.domain.Choice;
import com.cruru.question.domain.Question;
import com.cruru.question.domain.QuestionType;
import com.cruru.question.domain.repository.QuestionRepository;
import com.cruru.question.exception.QuestionNotFoundException;
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
    public Question create(QuestionCreateRequest request, ApplyForm applyForm) {
        Question savedQuestion = questionRepository.save(toQuestion(request, applyForm));

        if (savedQuestion.hasChoice()) {
            choiceService.createAll(request.choices(), savedQuestion);
        }

        return savedQuestion;
    }

    private Question toQuestion(QuestionCreateRequest request, ApplyForm applyForm) {
        return new Question(
                QuestionType.valueOf(request.type()),
                request.question(),
                request.orderIndex(),
                request.required(),
                applyForm
        );
    }

    @Transactional
    public void deleteAllByApplyForm(ApplyForm applyForm) {
        List<Question> questions = questionRepository.findAllByApplyForm(applyForm);
        for (Question question : questions) {
            if (question.hasChoice()) {
                choiceService.deleteAllByQuestion(question);
            }
            questionRepository.delete(question);
        }
    }

    public Question findById(Long id) {
        return questionRepository.findById(id)
                .orElseThrow(QuestionNotFoundException::new);
    }

    public List<Question> findByApplyForm(ApplyForm applyForm) {
        return questionRepository.findAllByApplyForm(applyForm);
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
                question.getSequence(),
                getChoiceResponses(question),
                question.isRequired()
        );
    }

    private List<ChoiceResponse> getChoiceResponses(Question question) {
        List<ChoiceResponse> choiceResponses = new ArrayList<>();
        if (question.hasChoice()) {
            List<Choice> choices = choiceService.findAllByQuestion(question);
            return choiceService.toChoiceResponses(choices);
        }
        return choiceResponses;
    }
}
