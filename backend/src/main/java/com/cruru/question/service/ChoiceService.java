package com.cruru.question.service;

import com.cruru.question.controller.request.ChoiceCreateRequest;
import com.cruru.question.controller.response.ChoiceResponse;
import com.cruru.question.domain.Choice;
import com.cruru.question.domain.repository.ChoiceRepository;
import com.cruru.question.exception.badrequest.ChoiceEmptyException;
import com.cruru.question.exception.badrequest.ChoiceIllegalSaveException;
import com.cruru.question.domain.Question;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChoiceService {

    private final ChoiceRepository choiceRepository;

    @Transactional
    public List<Choice> createAll(List<ChoiceCreateRequest> requests, Question question) {
        if (!question.hasChoice()) {
            throw new ChoiceIllegalSaveException();
        }
        if (requests.isEmpty()) {
            throw new ChoiceEmptyException();
        }
        return choiceRepository.saveAll(toChoices(requests, question));
    }

    private List<Choice> toChoices(List<ChoiceCreateRequest> requests, Question question) {
        return requests.stream()
                .map(request -> new Choice(request.choice(), request.orderIndex(), question))
                .toList();
    }

    public List<Choice> findAllByQuestion(Question question) {
        if (question.hasChoice()) {
            return choiceRepository.findAllByQuestion(question);
        }
        return List.of();
    }

    public List<ChoiceResponse> toChoiceResponses(List<Choice> choices) {
        return choices.stream()
                .map(this::toChoiceResponse)
                .toList();
    }

    private ChoiceResponse toChoiceResponse(Choice choice) {
        return new ChoiceResponse(choice.getId(), choice.getContent(), choice.getSequence());
    }

    @Transactional
    public void deleteAllByQuestion(Question question) {
        choiceRepository.deleteAllByQuestion(question);
    }
}
