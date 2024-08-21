package com.cruru.auth.util;

import com.cruru.member.domain.Member;

public interface SecureResource {

    boolean isAuthorizedBy(Member member);
}
