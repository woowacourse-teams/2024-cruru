package com.cruru.applicant.service;

import com.cruru.answer.domain.Answer;
import com.cruru.answer.domain.repository.AnswerRepository;
import com.cruru.applicant.controller.dto.ApplicantDetailResponse;
import com.cruru.applicant.controller.dto.ApplicantMoveRequest;
import com.cruru.applicant.controller.dto.ApplicantResponse;
import com.cruru.applicant.controller.dto.QuestionAndResponse;
import com.cruru.applicant.domain.Applicant;
import com.cruru.applicant.domain.repository.ApplicantRepository;
import com.cruru.applicant.exception.ApplicantNotFoundException;
import com.cruru.choice.domain.Choice;
import com.cruru.chosenresponse.domain.ChosenResponse;
import com.cruru.chosenresponse.domain.repository.ChosenResponseRepository;
import com.cruru.dashboard.domain.Dashboard;
import com.cruru.process.domain.Process;
import com.cruru.process.domain.repository.ProcessRepository;
import com.cruru.process.exception.ProcessNotFoundException;
import com.cruru.question.domain.Question;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ApplicantService {

    private final ApplicantRepository applicantRepository;
    private final ProcessRepository processRepository;
    private final AnswerRepository answerRepository;
    private final ChosenResponseRepository chosenResponseRepository;

    @Transactional
    public void updateApplicantProcess(long processId, ApplicantMoveRequest moveRequest) {
        Process process = processRepository.findById(processId)
                .orElseThrow(ProcessNotFoundException::new);

        List<Applicant> applicants = applicantRepository.findAllById(moveRequest.applicantIds());
        applicants.forEach(applicant -> applicant.updateProcess(process));
    }

    public ApplicantResponse findById(long id) {
        Applicant applicant = applicantRepository.findById(id)
                .orElseThrow(ApplicantNotFoundException::new);
        return toApplicantResponse(applicant);
    }

    private ApplicantResponse toApplicantResponse(Applicant applicant) {
        return new ApplicantResponse(
                applicant.getId(),
                applicant.getName(),
                applicant.getEmail(),
                applicant.getPhone(),
                applicant.getCreatedDate()
        );
    }

    public ApplicantDetailResponse findDetailById(long id) {
        Applicant applicant = applicantRepository.findById(id)
                .orElseThrow(ApplicantNotFoundException::new);
        List<Answer> answers = answerRepository.findAllByApplicantId(id);
        List<ChosenResponse> chosenResponses = chosenResponseRepository.findAllByApplicantId(id);
        Dashboard dashboard = applicant.getDashboard();
        List<QuestionAndResponse> questionAndResponses = toQuestionAndResponses(answers, chosenResponses);
        return new ApplicantDetailResponse(applicant.getName(), dashboard.getName(), questionAndResponses);
    }

    private List<QuestionAndResponse> toQuestionAndResponses(
            List<Answer> answers,
            List<ChosenResponse> chosenResponses
    ) {
        Stream<QuestionAndResponse> questionAndAnswers = answers.stream()
                .map(this::toQuestionAndResponse);
        Stream<QuestionAndResponse> questionAndChoices = chosenResponses.stream()
                .map(ChosenResponse::getChoice)
                .map(this::toQuestionAndResponse);
        return Stream.concat(questionAndAnswers, questionAndChoices)
                .sorted(Comparator.comparingInt(QuestionAndResponse::sequence))
                .toList();
    }

    private QuestionAndResponse toQuestionAndResponse(Answer answer) {
        Question question = answer.getQuestion();
        return new QuestionAndResponse(question.getSequence(), question.getContent(), answer.getContent());
    }

    private QuestionAndResponse toQuestionAndResponse(Choice choice) {
        Question question = choice.getQuestion();
        return new QuestionAndResponse(question.getSequence(), question.getContent(), choice.getContent());
    }
}
