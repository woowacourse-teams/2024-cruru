package com.cruru.email.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.cruru.email.domain.Email;
import com.cruru.util.RepositoryTest;
import com.cruru.util.fixture.EmailFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("발송 내역 레포지토리 테스트")
class EmailRepositoryTest extends RepositoryTest {

    @Autowired
    private EmailRepository emailRepository;

    @BeforeEach
    void setUp() {
        emailRepository.deleteAllInBatch();
    }

    @DisplayName("이미 DB에 저장되어 있는 ID를 가진 이메일 발송 내역을 저장하면, 해당 ID의 이메일 발송 내역은 후에 작성된 정보로 업데이트한다.")
    @Test
    void sameIdUpdate() {
        //given
        Email email = EmailFixture.approveEmail();
        Email saved = emailRepository.save(email);

        //when
        Email updatedEmail = new Email(
                email.getId(),
                null,
                null,
                EmailFixture.SUBJECT,
                EmailFixture.REJECT_CONTENT,
                true
        );
        emailRepository.save(updatedEmail);

        //then
        Email findEmail = emailRepository.findById(saved.getId()).get();
        assertThat(findEmail.getContent()).isEqualTo(EmailFixture.REJECT_CONTENT);
    }

    @DisplayName("ID가 없는 이메일 발송 내역을 저장하면, ID를 순차적으로 부여하여 저장한다.")
    @Test
    void saveNoId() {
        //given
        Email email1 = EmailFixture.approveEmail();
        Email email2 = EmailFixture.rejectEmail();

        //when
        Email savedEmail1 = emailRepository.save(email1);
        Email savedEmail2 = emailRepository.save(email2);

        //then
        assertThat(savedEmail1.getId() + 1).isEqualTo(savedEmail2.getId());
    }
}
