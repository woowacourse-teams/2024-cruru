package com.cruru.applicant.service;

import com.cruru.applicant.domain.dto.ApplicantCsvLine;
import com.cruru.applicant.domain.dto.ApplicantQuestionAnswerDto;
import com.cruru.applicant.domain.dto.ApplicantRowDto;
import com.cruru.applicant.domain.repository.ApplicantRepository;
import com.cruru.applicant.util.CsvUtil;
import com.cruru.question.domain.Question;
import com.cruru.question.domain.repository.QuestionRepository;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CsvExportService {

    private final QuestionRepository questionRepository;
    private final ApplicantRepository applicantRepository;

    public ByteArrayInputStream exportApplicantsToCsv(long applyFormId) {
        List<Question> questions = questionRepository.findByApplyFormId(applyFormId);

        List<ApplicantQuestionAnswerDto> rows = applicantRepository.findApplicantQuestionAnswers(applyFormId);

        Map<Long, ApplicantRowDto> applicantMap = groupApplicantsById(rows);

        List<ApplicantCsvLine> csvLines = createCsvLines(applicantMap, questions);

        return CsvUtil.writeToCsv(csvLines, questions);
    }


    private Map<Long, ApplicantRowDto> groupApplicantsById(List<ApplicantQuestionAnswerDto> rows) {
        Map<Long, ApplicantRowDto> applicantMap = new LinkedHashMap<>();

        for (ApplicantQuestionAnswerDto row : rows) {
            Long applicantId = row.applicantId();

            applicantMap.putIfAbsent(applicantId,
                    new ApplicantRowDto(
                            row.applicantName(),
                            row.email(),
                            row.phone(),
                            row.submissionDate(),
                            new HashMap<>()
                    )
            );
            ApplicantRowDto applicantRow = applicantMap.get(applicantId);
            String key = String.valueOf(row.questionId());

            applicantRow.questionAnswers().merge(
                    key,
                    row.answerContent(),
                    (existingAnswer, newAnswer) -> existingAnswer + ", " + newAnswer
            );
        }

        return applicantMap;
    }

    private List<ApplicantCsvLine> createCsvLines(
            Map<Long, ApplicantRowDto> applicantMap,
            List<Question> questions
    ) {
        List<ApplicantCsvLine> csvLines = new ArrayList<>();

        for (ApplicantRowDto rowDto : applicantMap.values()) {
            List<String> questionCols = new ArrayList<>();
            for (Question q : questions) {
                String key = String.valueOf(q.getId());
                String answer = rowDto.questionAnswers().getOrDefault(key, "");
                questionCols.add(answer);
            }

            csvLines.add(new ApplicantCsvLine(
                    rowDto.name(),
                    rowDto.email(),
                    rowDto.phone(),
                    rowDto.submissionDate(),
                    questionCols
            ));
        }
        return csvLines;
    }
}
