package com.cruru.question.service;

import com.cruru.applicant.domain.Applicant;
import com.cruru.applyform.controller.request.AnswerCreateRequest;
import com.cruru.applyform.exception.badrequest.ReplyNotExistsException;
import com.cruru.question.controller.response.AnswerResponse;
import com.cruru.question.domain.Answer;
import com.cruru.question.domain.Question;
import com.cruru.question.domain.repository.AnswerRepository;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AnswerService {

    private static final String DELIMITER = ", ";
    private static final String NOT_REPLIED = "";
    private final AnswerRepository answerRepository;

    @Transactional
    public void saveAnswerReplies(AnswerCreateRequest answerCreateRequest, Question question, Applicant applicant) {
        List<String> replies = answerCreateRequest.replies();
        if (question.isRequired() && replies.isEmpty()) {
            throw new ReplyNotExistsException();
        }
        if (!question.isRequired() && replies.isEmpty()) {
            answerRepository.save(new Answer(NOT_REPLIED, question, applicant));
        }
        for (String reply : replies) {
            Answer answer = new Answer(reply, question, applicant);
            answerRepository.save(answer);
        }
    }

    public List<Answer> findAllByApplicantWithQuestions(Applicant applicant) {
        return answerRepository.findAllByApplicantWithQuestions(applicant);
    }

    public List<AnswerResponse> toAnswerResponses(List<Answer> answers) {
        Map<Question, AnswerResponse> answerResponses = answers.stream()
                .collect(Collectors.toMap(
                        Answer::getQuestion,
                        this::createAnswerResponse,
                        this::mergeAnswers,
                        LinkedHashMap::new
                ));

        return new ArrayList<>(answerResponses.values());
    }

    private AnswerResponse createAnswerResponse(Answer answer) {
        Question question = answer.getQuestion();
        return new AnswerResponse(question.getSequence(), question.getContent(), answer.getContent());
    }

    private AnswerResponse mergeAnswers(AnswerResponse existing, AnswerResponse newResponse) {
        String mergedContent = existing.answer() + DELIMITER + newResponse.answer();
        return new AnswerResponse(existing.sequence(), existing.question(), mergedContent);
    }

    @Transactional
    public void deleteAllByApplicants(List<Applicant> applicants) {
        answerRepository.deleteAllByApplicantIds(applicants);
    }
}
