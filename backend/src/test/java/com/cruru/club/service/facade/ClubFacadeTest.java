package com.cruru.club.service.facade;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.cruru.club.controller.dto.ClubCreateRequest;
import com.cruru.club.domain.Club;
import com.cruru.member.domain.Member;
import com.cruru.member.domain.repository.MemberRepository;
import com.cruru.util.ServiceTest;
import com.cruru.util.fixture.MemberFixture;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("동아리 파사드 서비스 테스트")
class ClubFacadeTest extends ServiceTest {

    @Autowired
    private ClubFacade clubFacade;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private EntityManager entityManager;

    @DisplayName("회원가입 되어있는 회원이 새로운 동아리를 생성한다.")
    @Test
    void create() {
        // given
        Member member = memberRepository.save(MemberFixture.DOBBY);
        ClubCreateRequest request = new ClubCreateRequest("연합동아리");

        // when
        long clubId = clubFacade.create(request, member.getId());

        // then
        Club actual = entityManager.createQuery(
                        "SELECT c FROM Club c JOIN FETCH c.member WHERE c.id = :id", Club.class)
                .setParameter("id", clubId)
                .getSingleResult();
        assertAll(
                () -> assertThat(actual.getMember()).isEqualTo(member),
                () -> assertThat(actual.getName()).isEqualTo(request.name())
        );
    }
}
