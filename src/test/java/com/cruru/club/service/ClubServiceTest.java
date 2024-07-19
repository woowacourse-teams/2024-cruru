package com.cruru.club.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.cruru.applicant.domain.repository.ApplicantRepository;
import com.cruru.club.controller.dto.ClubCreateRequest;
import com.cruru.club.domain.Club;
import com.cruru.club.domain.repository.ClubRepository;
import com.cruru.club.domain.service.ClubService;
import com.cruru.dashboard.domain.repository.DashboardRepository;
import com.cruru.member.domain.Member;
import com.cruru.member.domain.repository.MemberRepository;
import com.cruru.process.domain.repository.ProcessRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@DisplayName("동아리 서비스 테스트")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class ClubServiceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ClubRepository clubRepository;

    @Autowired
    private ProcessRepository processRepository;

    @Autowired
    private ApplicantRepository applicantRepository;

    @Autowired
    private DashboardRepository dashboardRepository;

    @Autowired
    private ClubService clubService;

    private Member member;

    @BeforeEach
    void setUp() {
        applicantRepository.deleteAllInBatch();
        processRepository.deleteAllInBatch();
        dashboardRepository.deleteAllInBatch();
        clubRepository.deleteAllInBatch();
        memberRepository.deleteAllInBatch();
    }

    @DisplayName("새로운 동아리를 생성한다.")
    @Test
    void create() {
        // given
        member = memberRepository.save(new Member("email@example.com", "password", "phoneNumber"));
        ClubCreateRequest request = new ClubCreateRequest("연합동아리");

        // when
        long clubId = clubService.create(request, member.getId());

        // then
        Club club = clubRepository.findById(clubId).get();
        assertAll(
                () -> assertThat(club.getMember()).isEqualTo(member),
                () -> assertThat(club.getName()).isEqualTo(request.name())
        );
    }
}
