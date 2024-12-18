package com.cruru.member.facade;

import com.cruru.club.service.ClubService;
import com.cruru.email.service.EmailRedisClient;
import com.cruru.member.controller.request.EmailChangeRequest;
import com.cruru.member.controller.request.MemberCreateRequest;
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
    private final EmailRedisClient emailRedisClient;

    @Transactional
    public long create(MemberCreateRequest request) {
        emailRedisClient.verifyEmail(request.email());
        Member savedMember = memberService.create(request);
        clubService.create(request.clubName(), savedMember);
        return savedMember.getId();
    }

    @Transactional
    public void changeEmail(EmailChangeRequest request, long memberId) {
        emailRedisClient.verifyEmail(request.email());
        memberService.update(memberId, request.email());
    }
}
