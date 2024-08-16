package com.cruru.club.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.cruru.club.controller.dto.ClubCreateRequest;
import com.cruru.club.domain.Club;
import com.cruru.club.domain.repository.ClubRepository;
import com.cruru.member.domain.Member;
import com.cruru.member.domain.repository.MemberRepository;
import com.cruru.util.ServiceTest;
import com.cruru.util.fixture.ClubFixture;
import com.cruru.util.fixture.MemberFixture;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@DisplayName("동아리 서비스 테스트")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class ClubServiceTest extends ServiceTest {

    @Autowired
    private ClubService clubService;

    @Autowired
    private ClubRepository clubRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private EntityManager entityManager;

    @DisplayName("새로운 동아리를 생성한다.")
    @Test
    void create() {
        // given
        Member member = memberRepository.save(MemberFixture.DOBBY);
        ClubCreateRequest request = new ClubCreateRequest("연합동아리");

        // when
        Club saved = clubService.create(request, member);

        // then
        Club actual = entityManager.createQuery(
                        "SELECT c FROM Club c JOIN FETCH c.member WHERE c.id = :id", Club.class)
                .setParameter("id", saved.getId())
                .getSingleResult();
        assertAll(
                () -> assertThat(actual.getMember()).isEqualTo(member),
                () -> assertThat(actual.getName()).isEqualTo(request.name())
        );
    }

    @DisplayName("동아리를 ID로 조회한다.")
    @Test
    void findById() {
        // given
        Club savedClub = clubRepository.save(ClubFixture.create());
        Club actual = clubService.findById(savedClub.getId());

        // when&then
        assertAll(
                () -> assertDoesNotThrow(() -> clubService.findById(savedClub.getId())),
                () -> assertThat(actual.getName()).isEqualTo(savedClub.getName())
        );
    }
}
