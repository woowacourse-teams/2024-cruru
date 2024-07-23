package com.cruru.club.service;

import static com.cruru.fixture.MemberFixture.createMember;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.cruru.club.controller.dto.ClubCreateRequest;
import com.cruru.club.domain.Club;
import com.cruru.member.domain.Member;
import com.cruru.member.domain.repository.MemberRepository;
import com.cruru.util.ServiceTest;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@DisplayName("동아리 서비스 테스트")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class ClubServiceTest extends ServiceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ClubService clubService;

    @Autowired
    private EntityManager entityManager;

    @DisplayName("새로운 동아리를 생성한다.")
    @Test
    void create() {
        // given
        Member member = memberRepository.save(createMember());
        ClubCreateRequest request = new ClubCreateRequest("연합동아리");

        // when
        long clubId = clubService.create(request, member.getId());

        // then
        Club club = entityManager.createQuery(
                        "SELECT c FROM Club c JOIN FETCH c.member WHERE c.id = :id", Club.class)
                .setParameter("id", clubId)
                .getSingleResult();
        assertAll(
                () -> assertThat(club.getMember()).isEqualTo(member),
                () -> assertThat(club.getName()).isEqualTo(request.name())
        );
    }
}
