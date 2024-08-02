package com.cruru;

import static com.cruru.question.domain.QuestionType.DROPDOWN;
import static com.cruru.question.domain.QuestionType.SHORT_ANSWER;

import com.cruru.answer.domain.Answer;
import com.cruru.answer.domain.repository.AnswerRepository;
import com.cruru.applicant.domain.Applicant;
import com.cruru.applicant.domain.repository.ApplicantRepository;
import com.cruru.applyform.domain.ApplyForm;
import com.cruru.applyform.domain.repository.ApplyFormRepository;
import com.cruru.choice.domain.Choice;
import com.cruru.choice.domain.repository.ChoiceRepository;
import com.cruru.club.domain.Club;
import com.cruru.club.domain.repository.ClubRepository;
import com.cruru.dashboard.domain.Dashboard;
import com.cruru.dashboard.domain.repository.DashboardRepository;
import com.cruru.evaluation.domain.Evaluation;
import com.cruru.evaluation.domain.repository.EvaluationRepository;
import com.cruru.member.domain.Member;
import com.cruru.member.domain.repository.MemberRepository;
import com.cruru.process.domain.Process;
import com.cruru.process.domain.repository.ProcessRepository;
import com.cruru.question.domain.Question;
import com.cruru.question.domain.repository.QuestionRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component
@Profile("!test")
@RequiredArgsConstructor
public class DataLoader implements ApplicationRunner {

    private final MemberRepository memberRepository;
    private final ClubRepository clubRepository;
    private final DashboardRepository dashboardRepository;
    private final ProcessRepository processRepository;
    private final ApplicantRepository applicantRepository;
    private final QuestionRepository questionRepository;
    private final ChoiceRepository choiceRepository;
    private final AnswerRepository answerRepository;
    private final EvaluationRepository evaluationRepository;
    private final ApplyFormRepository applyFormRepository;

    @Value("${dataloader.enable}")
    private boolean enableDataLoader;

    @Override
    public void run(ApplicationArguments args) {
        if (enableDataLoader) {
            runDataLoader();
        }
    }

    private void runDataLoader() {
        Member member = new Member(1L, "member@mail.com", "qwer1234", "01012345678");
        memberRepository.save(member);
        Club club = new Club(1L, "크루루", member);
        clubRepository.save(club);
        Dashboard dashboard = new Dashboard(1L, club);
        LocalDateTime startDate = LocalDateTime.MIN;
        LocalDateTime dueDate = LocalDateTime.MAX;
        dashboardRepository.save(dashboard);
        ApplyForm applyForm = new ApplyForm(1L, "크루루 모집 공고", "# 모집 설명이다.", "www.cruru.kr/form/1", startDate, dueDate,
                dashboard);
        applyFormRepository.save(applyForm);

        Process firstProcess = new Process(1L, 0, "서류 전형", "지원 서류를 확인한다.", dashboard);
        Process lastProcess = new Process(2L, 1, "최종 합격", "최종 합격자", dashboard);
        processRepository.save(firstProcess);
        processRepository.save(lastProcess);

        Applicant lurgi = new Applicant(1L, "러기", "lurg@mail.com", "01011111111", firstProcess, false);
        Applicant dobby = new Applicant(2L, "도비", "dobb@mail.com", "01022222222", firstProcess, true);
        Applicant arrr = new Applicant(3L, "아르", "arrr@mail.com", "01033333333", lastProcess, false);
        Applicant chocochip = new Applicant(4L, "초코칩", "choc@mail.com", "01044444444", lastProcess, false);
        Applicant myungoh = new Applicant(5L, "명오", "myun@mail.com", "01055555555", lastProcess, false);
        Applicant rush = new Applicant(6L, "러시", "rush@mail.com", "01066666666", firstProcess, false);
        Applicant nyangin = new Applicant(7L, "냥인", "nyan@mail.com", "01077777777", firstProcess, false);
        Applicant redpanda = new Applicant(8L, "렛서", "pand@mail.com", "01088888888", firstProcess, false);
        List<Applicant> applicants = List.of(lurgi, dobby, arrr, chocochip, myungoh, rush, nyangin, redpanda);
        applicantRepository.saveAll(applicants);

        Question choiceQuestion = questionRepository.save(new Question(1L, DROPDOWN, "성별", "", 0, applyForm));
        Question essayQuestion = questionRepository.save(
                new Question(SHORT_ANSWER, "좋아하는 숫자가 무엇인가요?", "정수로 입력해주세요.", 1, applyForm));

        Choice maleChoice = choiceRepository.save(new Choice(1L, "남", 1, choiceQuestion));
        Choice femaleChoice = choiceRepository.save(new Choice(2L, "여", 2, choiceQuestion));

        List<Answer> answers = List.of(
                new Answer(1L, maleChoice.getContent(), choiceQuestion, lurgi),
                new Answer(2L, maleChoice.getContent(), choiceQuestion, dobby),
                new Answer(3L, maleChoice.getContent(), choiceQuestion, arrr),
                new Answer(4L, maleChoice.getContent(), choiceQuestion, chocochip),
                new Answer(5L, maleChoice.getContent(), choiceQuestion, myungoh),
                new Answer(6L, maleChoice.getContent(), choiceQuestion, rush),
                new Answer(7L, femaleChoice.getContent(), choiceQuestion, nyangin),
                new Answer(8L, femaleChoice.getContent(), choiceQuestion, redpanda),

                new Answer(9L, "1", essayQuestion, lurgi),
                new Answer(10L, "2", essayQuestion, dobby),
                new Answer(11L, "3", essayQuestion, arrr),
                new Answer(12L, "4", essayQuestion, chocochip),
                new Answer(13L, "5", essayQuestion, myungoh),
                new Answer(14L, "6", essayQuestion, rush),
                new Answer(15L, "7", essayQuestion, nyangin),
                new Answer(16L, "8", essayQuestion, redpanda)
        );
        answerRepository.saveAll(answers);

        List<Evaluation> evaluations = List.of(
                new Evaluation(1L, 5, "잘생겼다.", firstProcess, lurgi),
                new Evaluation(2L, 4, "", lastProcess, lurgi),
                new Evaluation(3L, 3, "", firstProcess, dobby),
                new Evaluation(4L, 5, "인상이 선하다.", lastProcess, dobby),
                new Evaluation(5L, 3, "경력이 뛰어나다.", firstProcess, arrr),
                new Evaluation(6L, 4, "", lastProcess, arrr),
                new Evaluation(7L, 4, "똑똑하다.", firstProcess, chocochip),
                new Evaluation(8L, 5, "진짜 똑똑하다.", lastProcess, chocochip),
                new Evaluation(9L, 2, "열정있다.", firstProcess, myungoh),
                new Evaluation(10L, 1, "", lastProcess, myungoh),
                new Evaluation(11L, 5, "빠르다.", firstProcess, rush),
                new Evaluation(12L, 1, "아닌 것 같다.", lastProcess, rush),
                new Evaluation(13L, 4, "꼼꼼하다.", firstProcess, nyangin),
                new Evaluation(14L, 4, "전과 같다.", lastProcess, nyangin),
                new Evaluation(15L, 3, "그림을 잘그린다.", firstProcess, redpanda),
                new Evaluation(16L, 4, "", lastProcess, redpanda)
        );
        evaluationRepository.saveAll(evaluations);
    }
}
