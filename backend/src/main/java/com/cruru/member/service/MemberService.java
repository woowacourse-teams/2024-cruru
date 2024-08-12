package com.cruru.member.service;

import com.cruru.member.controller.dto.MemberCreateRequest;
import com.cruru.member.domain.Member;
import com.cruru.member.domain.repository.MemberRepository;
import com.cruru.member.exception.MemberNotFoundException;
import com.cruru.member.exception.badrequest.MemberEmailDuplicatedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Member create(MemberCreateRequest request) {
        boolean exists = memberRepository.existsByEmail(request.email());
        if (exists) {
            throw new MemberEmailDuplicatedException();
        }

        return memberRepository.save(new Member(request.email(), request.password(), request.phone()));
    }


    public Member findById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(MemberNotFoundException::new);
    }
}
