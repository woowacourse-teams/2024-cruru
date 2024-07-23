package com.cruru.fixture;

import com.cruru.member.domain.Member;

public class MemberFixture {

    public static Member createMember() {
        return new Member("mem@email.com", "mempw1234", "01000000000");
    }
}
