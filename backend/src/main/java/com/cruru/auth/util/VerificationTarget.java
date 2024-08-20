package com.cruru.auth.util;

import com.cruru.member.domain.Member;

public interface VerificationTarget {

    boolean isAuthenticated(Member member);
}
