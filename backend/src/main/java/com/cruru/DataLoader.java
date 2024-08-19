package com.cruru;

import static com.cruru.applicant.domain.ApplicantState.APPROVED;
import static com.cruru.applicant.domain.ApplicantState.PENDING;
import static com.cruru.applicant.domain.ApplicantState.REJECTED;
import static com.cruru.question.domain.QuestionType.LONG_ANSWER;
import static com.cruru.question.domain.QuestionType.MULTIPLE_CHOICE;
import static com.cruru.question.domain.QuestionType.SHORT_ANSWER;
import static com.cruru.question.domain.QuestionType.SINGLE_CHOICE;

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
        Member member = new Member("member@mail.com", "$2a$10$rG0JsflKdGcORjGFTURYb.npEgtvClK4.3P.EMr/o3SdekrVFxOvG",
                "01012345678"); // password 원문: qwer1234
        memberRepository.save(member);
        Club club = new Club("우아한테크코스", member);
        clubRepository.save(club);
        Dashboard dashboard = new Dashboard(club);
        LocalDateTime startDate = LocalDateTime.of(2024, 10, 6, 15, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2024, 10, 16, 10, 0, 0);
        dashboardRepository.save(dashboard);
        String description = """
                             <h2>2025 신입생 (7기) 선발 일정 </h2><p><br></p><ol><li data-list=\"bullet\"><span class=\"ql-ui\" contenteditable=\"false\"></span><strong>서류접수:</strong> 2024년 10월 6일(금) 오후 3시 ~ 10월 16일(월) 오전 10시</li><li data-list=\"bullet\"><span class=\"ql-ui\" contenteditable=\"false\"></span><strong>프리코스:</strong> 2024년 10월 19일(목) ~ 11월 15일(수)</li><li data-list=\"bullet\"><span class=\"ql-ui\" contenteditable=\"false\"></span><strong>1차 합격자 발표:</strong> 2024년 12월 11일(월) 오후 3시, 개별 E-mail 통보</li><li data-list=\"bullet\"><span class=\"ql-ui\" contenteditable=\"false\"></span><strong>최종 코딩 테스트:</strong> 2024년 12월 16일(토)</li><li data-list=\"bullet\"><span class=\"ql-ui\" contenteditable=\"false\"></span><strong>최종 합격자 발표:</strong> 2024년 12월 27일(수) 오후 3시, 개별 E-mail 통보 </li></ol><p><br></p><h2>2025 신입생 (7기) 교육 기간 </h2><p><br></p><ol><li data-list=\"bullet\"><span class=\"ql-ui\" contenteditable=\"false\"></span>2025년 2월 ~ 11월 (약 10개월) </li></ol>
                             """;
        ApplyForm applyForm = new ApplyForm(
                "우아한테크코스 2025 백엔드 신입생 모집 ",
                description,
                "https://woowacourse.io/apply",
                startDate,
                endDate,
                dashboard
        );
        applyFormRepository.save(applyForm);

        Process firstProcess = new Process(0, "프리코스", "지원 서류를 확인한다.", dashboard);
        Process codingTest = new Process(1, "최종 코딩 테스트", "최종 코딩 테스트 전형", dashboard);
        Process lastProcess = new Process(2, "최종 합격", "최종 합격자", dashboard);
        processRepository.saveAll(List.of(firstProcess, codingTest, lastProcess));

        Applicant lurgi = new Applicant(1L, "러기", "lurg@mail.com", "01011111111", firstProcess, PENDING);
        Applicant dobby = new Applicant(2L, "도비", "dobb@mail.com", "01022222222", firstProcess, APPROVED);
        Applicant arrr = new Applicant(3L, "아르", "arrr@mail.com", "01033333333", lastProcess, REJECTED);
        Applicant chocochip = new Applicant(4L, "초코칩", "choc@mail.com", "01044444444", lastProcess, PENDING);
        Applicant myungoh = new Applicant(5L, "명오", "myun@mail.com", "01055555555", lastProcess, PENDING);
        Applicant rush = new Applicant(6L, "러시", "rush@mail.com", "01066666666", firstProcess, PENDING);
        Applicant nyangin = new Applicant(7L, "냥인", "nyan@mail.com", "01077777777", firstProcess, PENDING);
        Applicant redpanda = new Applicant(8L, "렛서", "pand@mail.com", "01088888888", firstProcess, PENDING);
        List<Applicant> applicants = List.of(lurgi, dobby, arrr, chocochip, myungoh, rush, nyangin, redpanda);
        applicantRepository.saveAll(applicants);

        Question essayQuestion = questionRepository.save(
                new Question(SHORT_ANSWER, "좋아하는 숫자가 무엇인가요?", "정수로 입력해주세요.", 1, false, applyForm));

        String description1 = """
                              프로그래밍 학습을 하고 장기간 개발자로 살아가기 위해, 본인만의 효과적인 학습 방식을 찾는 것은 매우 중요합니다. 
                              프로그래밍이 아니더라도 지금까지의 모든 학습 경험을 되돌아봤을 때, 본인에게 유용했던 학습 방식을 찾아낸 과정과 경험을 공유해 주세요. 
                              그리고 이 경험은 현재 본인의 프로그래밍 학습 과정에 어떻게 적용되고 있나요? (1000자 이내)""";
        Question question1 = questionRepository.save(
                new Question(LONG_ANSWER, "효과적인 학습 방식과 경험", description1, 1, false, applyForm)
        );

        String description2 = """
                              좋은 개발자가 되려면 성장 과정에서 많은 실패와 어려움이 기다리고 있습니다. 
                              나의 성장을 위한 경험 중 실패나 어려움을 해결하기 위해 어떤 시도를 했고 여기서 얻은 배움은 무엇인가요? 
                              그리고 이런 경험이 현재 여러분의 성장 방식에 어떤 영향을 주고 있는지 이야기해 주세요.
                              (혼자 겪은 경험도 좋고, 타인과 함께한 협업과정에서의 실패와 어려움도 좋습니다.) (1000자 이내)""";
        Question question2 = questionRepository.save(
                new Question(LONG_ANSWER, "성장 중 겪은 실패와 극복", description2, 2, false, applyForm)
        );

        String description3 = """
                              진정한 몰입은 즐거움과 동시에 성장을 이끌어냅니다.
                              여러분이 오랜 시간 동안 주도적으로 몰입했던 경험은 무엇이었나요? 
                              그 과정에서 어떤 도전을 마주했고, 이 경험으로 무엇을 배우고 얻었는지 '구체적으로' 이야기해 주세요.
                              (반드시 소프트웨어에 대한 경험이 아니어도 괜찮습니다. 만약 몰입한 경험의 결과물을 함께 보여주실 수 있다면 더 좋습니다.) (1000자 이내)""";
        Question question3 = questionRepository.save(
                new Question(LONG_ANSWER, "오랜 시간 몰입했던 경험 그리고 도전", description3, 3, false, applyForm)
        );

        String description4 = """
                              여러분이 되고 싶은 프로그래머의 모습은 무엇인가요?
                              그 모습에 가까워지기 위해 현재 어떤 구체적인 노력을 기울이고 있는지 알려주세요.
                              여기에 더해 만약 우아한테크코스가 없다고 가정한다면 어떻게 성장해 나갈 것인지도 함께 이야기해 주세요. (1000자 이내)""";
        Question question4 = questionRepository.save(
                new Question(LONG_ANSWER, "오랜 시간 몰입했던 경험 그리고 도전", description4, 4, false, applyForm)
        );

        String description5 = "우아한테크코스 소식을 처음 접한 곳을 선택해주세요.";
        Question question5 = questionRepository.save(
                new Question(MULTIPLE_CHOICE, "지원 경로", description5, 5, false, applyForm)
        );

        Choice homepage = choiceRepository.save(new Choice("우아한테크코스 홈페이지", 1, question5));
        Choice youtube = choiceRepository.save(new Choice("우아한테크코스 유튜브", 2, question5));
        Choice community = choiceRepository.save(new Choice("직무 관련 동아리/커뮤니티", 3, question5));
        Choice socialMedia = choiceRepository.save(new Choice("소셜 미디어", 4, question5));

        String description6 = "모든 문항에 답했는지 확인해주세요.";
        Question question6 = questionRepository.save(
                new Question(SINGLE_CHOICE, "제출 후에 수정이 불가능합니다.", description6, 6, false, applyForm)
        );

        Choice yes = choiceRepository.save(new Choice("네, 확인했습니다.", 1, question6));
        Choice no = choiceRepository.save(new Choice("다시 확인하겠습니다.", 2, question6));

        List<Answer> answers = List.of(
                new Answer("서울대학교 컴퓨터공학과 졸업", question1, lurgi),
                new Answer("카이스트 소프트웨어학과 재학 중", question1, dobby),
                new Answer("부산대학교 전자공학과 졸업", question1, arrr),
                new Answer("한양대학교 정보통신공학과 졸업", question1, chocochip),
                new Answer("고려대학교 정보보호학과 졸업", question1, myungoh),
                new Answer("연세대학교 컴퓨터과학과 재학 중", question1, rush),
                new Answer("성균관대학교 소프트웨어학과 졸업", question1, nyangin),
                new Answer("이화여자대학교 컴퓨터공학과 졸업", question1, redpanda),

                new Answer(
                        "저는 효율적인 학습을 위해 주로 플래닝과 시간을 잘 쪼개서 사용하는 방법을 활용합니다. 학습 계획을 세워 목표를 설정하고, 이를 달성하기 위해 매일 꾸준히 공부합니다. 이 방식은 프로그래밍 학습에도 큰 도움이 되고 있습니다.",
                        question1,
                        lurgi
                ),
                new Answer(
                        "혼자 학습하는 것보다 그룹 스터디를 통해 다른 사람들과 함께 공부하는 것이 저에게는 더 효과적이었습니다. 다양한 시각에서 문제를 바라보고 해결하는 데 큰 도움이 되었습니다.",
                        question1,
                        dobby
                ),
                new Answer(
                        "이해가 안 되는 부분은 여러 번 반복해서 공부하는 방식이 저에게 유효했습니다. 프로그래밍에서도 어려운 부분이 있을 때 계속 반복해서 코드를 작성하고 문제를 해결하면서 실력을 키워가고 있습니다.",
                        question1,
                        arrr
                ),
                new Answer(
                        "온라인 강의와 강의 노트를 병행하는 학습 방식이 저에게는 가장 효과적이었습니다. 이를 통해 개념을 더 명확히 이해하고 실전에 적용할 수 있었습니다.",
                        question1,
                        chocochip
                ),
                new Answer(
                        "프로젝트 기반 학습이 저에게는 가장 효과적이었습니다. 실제로 프로젝트를 수행하면서 배우는 것이 이론 학습보다 훨씬 더 잘 이해되고 기억에 오래 남았습니다.",
                        question1,
                        myungoh
                ),
                new Answer(
                        "퀴즈나 테스트를 통해 학습 내용을 점검하는 방식이 저에게는 유효했습니다. 이를 통해 어떤 부분이 약한지 파악하고, 그 부분을 집중적으로 공부할 수 있었습니다.",
                        question1,
                        rush
                ),
                new Answer(
                        "실제 문제를 풀어보면서 학습하는 것이 저에게는 가장 효과적이었습니다. 이를 통해 학습한 이론을 실전에 적용하고, 문제 해결 능력을 키울 수 있었습니다.",
                        question1,
                        nyangin
                ),
                new Answer(
                        "문제를 해결할 때마다 그 과정을 기록하고 복습하는 방식이 저에게는 가장 효과적이었습니다. 이를 통해 비슷한 문제를 다시 만났을 때 쉽게 해결할 수 있었습니다.",
                        question1,
                        redpanda
                ),

                new Answer(
                        "첫 번째 프로그래밍 프로젝트에서 많은 어려움을 겪었지만, 끈기 있게 문제를 해결하고 프로젝트를 완성했습니다. 이 경험을 통해 문제 해결 능력을 키웠고, 현재도 어려움이 닥쳤을 때 포기하지 않고 해결하는 데 큰 도움이 되고 있습니다.",
                        question2,
                        lurgi
                ),
                new Answer(
                        "팀 프로젝트에서 협업하는 과정에서 많은 어려움을 겪었지만, 팀원들과의 소통과 협력을 통해 문제를 해결했습니다. 이 경험을 통해 협업의 중요성을 깨달았고, 현재의 학습 과정에도 큰 영향을 미치고 있습니다.",
                        question2,
                        dobby
                ),
                new Answer(
                        "학습 중 여러 번 실패를 경험했지만, 그때마다 새로운 방법을 시도하며 문제를 해결했습니다. 이 경험은 저의 학습 방식에 큰 변화를 주었고, 현재도 다양한 시도를 통해 문제를 해결하고 있습니다.",
                        question2,
                        arrr
                ),
                new Answer(
                        "초기에는 프로그래밍 언어를 배우는 데 많은 어려움을 겪었지만, 꾸준히 학습하고 연습하면서 문제를 해결했습니다. 이 경험은 저에게 인내심과 꾸준함의 중요성을 일깨워주었고, 현재의 학습 방식에도 영향을 미치고 있습니다.",
                        question2,
                        chocochip
                ),
                new Answer(
                        "프로젝트 진행 중 여러 차례 문제에 부딪혔지만, 그때마다 팀원들과 함께 해결책을 모색하며 문제를 해결했습니다. 이 경험은 협업의 중요성을 깨닫게 했고, 현재의 학습 과정에도 큰 영향을 미치고 있습니다.",
                        question2,
                        myungoh
                ),
                new Answer(
                        "코딩 테스트에서 여러 번 실패를 경험했지만, 그때마다 부족한 부분을 보완하며 재도전했습니다. 이 경험은 저에게 끈기의 중요성을 일깨워주었고, 현재의 학습 방식에도 큰 영향을 미치고 있습니다.",
                        question2,
                        rush
                ),
                new Answer(
                        "프로그래밍 프로젝트에서 발생한 문제를 해결하기 위해 많은 노력을 기울였고, 결국 문제를 해결했습니다. 이 경험은 저에게 문제 해결 능력을 키워주었고, 현재의 학습 방식에도 큰 영향을 미치고 있습니다.",
                        question2,
                        nyangin
                ),
                new Answer(
                        "협업 프로젝트에서 발생한 갈등을 해결하기 위해 많은 노력을 기울였고, 결국 문제를 해결했습니다. 이 경험은 저에게 협력의 중요성을 깨닫게 했고, 현재의 학습 방식에도 큰 영향을 미치고 있습니다.",
                        question2,
                        redpanda
                ),

                new Answer(
                        "대학생 때, 인공지능 관련 프로젝트에 몰입하여 6개월 동안 연구와 개발에 매진했습니다. 이 과정에서 많은 도전을 마주했지만, 최종적으로 프로젝트를 성공적으로 마무리할 수 있었습니다. 이를 통해 문제 해결 능력과 인내심을 배울 수 있었습니다.",
                        question3,
                        lurgi
                ),
                new Answer(
                        "개인적으로 웹 애플리케이션을 개발하면서 오랜 시간 몰입했습니다. 이 과정에서 여러 가지 문제를 해결해야 했지만, 이를 통해 개발 능력과 문제 해결 능력을 키울 수 있었습니다.",
                        question3,
                        dobby
                ),
                new Answer(
                        "오픈 소스 프로젝트에 참여하여 오랜 시간 몰입했습니다. 이 과정에서 많은 도전을 마주했지만, 결국 프로젝트를 성공적으로 완료할 수 있었습니다. 이를 통해 협업 능력과 문제 해결 능력을 키울 수 있었습니다.",
                        question3,
                        arrr
                ),
                new Answer(
                        "대학 졸업 프로젝트에 몰입하여 1년 동안 연구와 개발에 매진했습니다. 이 과정에서 많은 도전을 마주했지만, 최종적으로 프로젝트를 성공적으로 마무리할 수 있었습니다. 이를 통해 문제 해결 능력과 인내심을 배울 수 있었습니다.",
                        question3,
                        chocochip
                ),
                new Answer(
                        "프로그래밍 경진대회에 참여하여 오랜 시간 몰입했습니다. 이 과정에서 여러 가지 문제를 해결해야 했지만, 이를 통해 개발 능력과 문제 해결 능력을 키울 수 있었습니다.",
                        question3,
                        myungoh
                ),
                new Answer(
                        "프리랜서 개발자로 일하면서 오랜 시간 몰입했습니다. 이 과정에서 많은 도전을 마주했지만, 이를 통해 문제 해결 능력과 인내심을 배울 수 있었습니다.",
                        question3,
                        rush
                ),
                new Answer(
                        "개인적으로 블로그를 운영하면서 오랜 시간 몰입했습니다. 이 과정에서 많은 도전을 마주했지만, 이를 통해 글쓰기 능력과 문제 해결 능력을 키울 수 있었습니다.",
                        question3,
                        nyangin
                ),
                new Answer(
                        "대학 동아리 활동을 통해 오랜 시간 몰입했습니다. 이 과정에서 많은 도전을 마주했지만, 이를 통해 협업 능력과 문제 해결 능력을 키울 수 있었습니다.",
                        question3,
                        redpanda
                ),

                new Answer(
                        "저는 존경받는 프로그래머가 되어 여러 사람에게 영감을 주고 싶습니다. 이를 위해 현재 매일 꾸준히 공부하고 있으며, 다양한 프로젝트에 참여하여 실력을 쌓고 있습니다. 만약 우아한테크코스가 없다면, 온라인 강의와 커뮤니티 활동을 통해 지속적으로 성장할 것입니다.",
                        question4,
                        lurgi
                ),
                new Answer(
                        "저는 문제 해결 능력이 뛰어난 프로그래머가 되고 싶습니다. 이를 위해 현재 다양한 문제를 풀고 있으며, 코드 리뷰를 통해 실력을 향상시키고 있습니다. 만약 우아한테크코스가 없다면, 독학과 스터디 그룹을 통해 지속적으로 성장할 것입니다.",
                        question4,
                        dobby
                ),
                new Answer(
                        "저는 협업 능력이 뛰어난 프로그래머가 되고 싶습니다. 이를 위해 현재 팀 프로젝트에 참여하고 있으며, 다양한 협업 도구를 익히고 있습니다. 만약 우아한테크코스가 없다면, 오픈 소스 프로젝트에 참여하여 지속적으로 성장할 것입니다.",
                        question4,
                        arrr
                ),
                new Answer(
                        "저는 창의적인 프로그래머가 되고 싶습니다. 이를 위해 현재 새로운 아이디어를 시도해 보고 있으며, 다양한 기술을 익히고 있습니다. 만약 우아한테크코스가 없다면, 개인 프로젝트를 통해 지속적으로 성장할 것입니다.",
                        question4,
                        chocochip
                ),
                new Answer(
                        "저는 효율적인 프로그래머가 되고 싶습니다. 이를 위해 현재 코딩 실력을 향상시키기 위해 노력하고 있으며, 다양한 도구를 익히고 있습니다. 만약 우아한테크코스가 없다면, 독학과 실무 경험을 통해 지속적으로 성장할 것입니다.",
                        question4,
                        myungoh
                ),
                new Answer(
                        "저는 커뮤니케이션 능력이 뛰어난 프로그래머가 되고 싶습니다. 이를 위해 현재 다양한 사람들과 소통하며 협업하는 방법을 익히고 있습니다. 만약 우아한테크코스가 없다면, 커뮤니티 활동을 통해 지속적으로 성장할 것입니다.",
                        question4,
                        rush
                ),
                new Answer(
                        "저는 문제 해결 능력이 뛰어난 프로그래머가 되고 싶습니다. 이를 위해 현재 다양한 문제를 풀고 있으며, 코드 리뷰를 통해 실력을 향상시키고 있습니다. 만약 우아한테크코스가 없다면, 독학과 스터디 그룹을 통해 지속적으로 성장할 것입니다.",
                        question4,
                        nyangin
                ),
                new Answer(
                        "저는 팀워크를 잘하는 프로그래머가 되고 싶습니다. 이를 위해 현재 팀 프로젝트에 참여하고 있으며, 다양한 협업 도구를 익히고 있습니다. 만약 우아한테크코스가 없다면, 오픈 소스 프로젝트에 참여하여 지속적으로 성장할 것입니다.",
                        question4,
                        redpanda
                ),

                new Answer(homepage.getContent(), question5, lurgi),
                new Answer(homepage.getContent(), question5, dobby),
                new Answer(youtube.getContent(), question5, arrr),
                new Answer(youtube.getContent(), question5, chocochip),
                new Answer(socialMedia.getContent(), question5, myungoh),
                new Answer(socialMedia.getContent(), question5, rush),
                new Answer(community.getContent(), question5, nyangin),
                new Answer(community.getContent(), question5, redpanda),

                new Answer(yes.getContent(), question6, lurgi),
                new Answer(yes.getContent(), question6, dobby),
                new Answer(yes.getContent(), question6, arrr),
                new Answer(yes.getContent(), question6, chocochip),
                new Answer(yes.getContent(), question6, myungoh),
                new Answer(yes.getContent(), question6, rush),
                new Answer(yes.getContent(), question6, nyangin),
                new Answer(yes.getContent(), question6, redpanda)
        );
        answerRepository.saveAll(answers);

        List<Evaluation> evaluations = List.of(
                new Evaluation(5, "우수한 실력", firstProcess, lurgi),
                new Evaluation(4, "좋은 잠재력", codingTest, lurgi),
                new Evaluation(3, "노력 필요", firstProcess, dobby),
                new Evaluation(5, "매우 긍정적", codingTest, dobby),
                new Evaluation(3, "성장 가능성", firstProcess, arrr),
                new Evaluation(4, "기본기 탄탄", codingTest, arrr),
                new Evaluation(4, "뛰어난 이해력", firstProcess, chocochip),
                new Evaluation(5, "매우 뛰어남", codingTest, chocochip),
                new Evaluation(2, "열정적", firstProcess, myungoh),
                new Evaluation(1, "개선 필요", codingTest, myungoh),
                new Evaluation(5, "빠른 학습 능력", firstProcess, rush),
                new Evaluation(1, "-> 불합격", codingTest, rush),
                new Evaluation(4, "꼼꼼함", firstProcess, nyangin),
                new Evaluation(4, "전과 동일", codingTest, nyangin),
                new Evaluation(3, "예술적 감각", firstProcess, redpanda),
                new Evaluation(4, "좋은 평가", codingTest, redpanda)
        );
        evaluationRepository.saveAll(evaluations);
    }
}
