package com.cruru.auth.util;

import com.cruru.advice.ForbiddenException;
import com.cruru.member.domain.Member;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthChecker {

    public static void validateAuthority(VerificationTarget authValidation, Member member) {
        if (!authValidation.isAuthenticated(member)) {
            throw new ForbiddenException();
        }
    }
}
