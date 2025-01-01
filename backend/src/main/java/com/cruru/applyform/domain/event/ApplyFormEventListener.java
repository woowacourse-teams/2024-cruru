package com.cruru.applyform.domain.event;

import com.cruru.applicant.domain.Applicant;
import com.cruru.applyform.domain.ApplyForm;
import com.cruru.club.domain.Club;
import com.cruru.email.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
@Slf4j
public class ApplyFormEventListener {

    private final EmailService emailService;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleApplyFormEvent(ApplyFormEvent event) {

        Club club = event.club();
        ApplyForm applyForm = event.applyForm();
        Applicant applicant = event.applicant();

        String subject = "지원 완료 안내";
        String content = String.format(
                "안녕하세요, %s님.\n\n%s 지원서가 성공적으로 제출되었습니다.\n\n감사합니다.",
                applicant.getName(),
                applyForm.getTitle()
        );

        emailService.send(
                club,
                applicant,
                subject,
                content,
                null
        );
    }
}
