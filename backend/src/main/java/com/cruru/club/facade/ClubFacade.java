package com.cruru.club.facade;

import com.cruru.club.controller.request.ClubCreateRequest;
import com.cruru.club.domain.Club;
import com.cruru.club.service.ClubService;
import com.cruru.member.domain.Member;
import com.cruru.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ClubFacade {

    private final ClubService clubService;
    private final MemberService memberService;

    @Transactional
    public Long create(ClubCreateRequest request, long memberId) {
        Member clubOwner = memberService.findById(memberId);
        Club createdClub = clubService.create(request, clubOwner);

        return createdClub.getId();
    }

    public Long findByMemberEmail(String email) {
        Member member = memberService.findByEmail(email);
        Club club = clubService.findByMember(member);
        return club.getId();
    }
}
