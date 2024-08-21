package com.cruru.member.service.facade;

import com.cruru.club.service.ClubService;
import com.cruru.member.controller.dto.MemberCreateRequest;
import com.cruru.member.domain.Member;
import com.cruru.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberFacade {

    private final MemberService memberService;
    private final ClubService clubService;

    @Transactional
    public long create(MemberCreateRequest request) {
        Member savedMember = memberService.create(request);
        clubService.create(request.clubName(), savedMember);
        return savedMember.getId();
    }
}
