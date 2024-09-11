package com.cruru.notice.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.cruru.notice.domain.Notice;
import com.cruru.notice.domain.NoticeType;
import com.cruru.util.RepositoryTest;
import com.cruru.util.fixture.NoticeFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("발송 내역 레포지토리 테스트")
class NoticeRepositoryTest extends RepositoryTest {
    
    @Autowired
    private NoticeRepository noticeRepository;

    @BeforeEach
    void setUp() {
        noticeRepository.deleteAllInBatch();
    }

    @DisplayName("이미 DB에 저장되어 있는 ID를 가진 프로세스를 저장하면, 해당 ID의 프로세스는 후에 작성된 정보로 업데이트한다.")
    @Test
    void sameIdUpdate() {
        //given
        Notice notice = NoticeFixture.approveNotice();
        Notice saved = noticeRepository.save(notice);

        //when
        Notice updatedNotice = new Notice(
                notice.getId(),
                null,
                null,
                "[우아한테크코스] 7기 최종 심사 결과 안내",
                "지원해주셔서 감사합니다. 불합격입니다.",
                NoticeType.EMAIL
        );
        noticeRepository.save(updatedNotice);

        //then
        Notice findNotice = noticeRepository.findById(saved.getId()).get();
        assertThat(findNotice.getText()).isEqualTo("지원해주셔서 감사합니다. 불합격입니다.");
    }

    @DisplayName("ID가 없는 프로세스를 저장하면, ID를 순차적으로 부여하여 저장한다.")
    @Test
    void saveNoId() {
        //given
        Notice notice1 = NoticeFixture.approveNotice();
        Notice notice2 = NoticeFixture.rejectNotice();

        //when
        Notice savedNotice1 = noticeRepository.save(notice1);
        Notice savedNotice2 = noticeRepository.save(notice2);

        //then
        assertThat(savedNotice1.getId() + 1).isEqualTo(savedNotice2.getId());
    }
}
