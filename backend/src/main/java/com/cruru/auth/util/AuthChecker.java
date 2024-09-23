package com.cruru.auth.util;

import com.cruru.advice.ForbiddenException;
import com.cruru.member.domain.Member;
import com.cruru.member.domain.MemberRole;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthChecker {

    public static void checkAuthority(SecureResource resource, Member member) {
        if (member.getRole() == MemberRole.ADMIN) {
            return;
        }

        if (!resource.isAuthorizedBy(member)) {
            throw new ForbiddenException();
        }
    }
}
