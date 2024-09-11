package com.cruru.auth.facade;

import com.cruru.auth.controller.request.LoginRequest;
import com.cruru.auth.exception.LoginFailedException;
import com.cruru.auth.service.AuthService;
import com.cruru.member.domain.Member;
import com.cruru.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthFacade {

    private final AuthService authService;
    private final MemberService memberService;

    public String login(LoginRequest request) {
        Member member = memberService.findByEmail(request.email());
        if (authService.isNotVerifiedPassword(request.password(), member.getPassword())) {
            throw new LoginFailedException();
        }
        return authService.createToken(member);
    }
}
