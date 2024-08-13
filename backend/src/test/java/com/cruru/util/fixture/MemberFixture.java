package com.cruru.util.fixture;

import com.cruru.member.domain.Member;

public class MemberFixture {

    public static Member createMember1() {
        return new Member("mem@email.com", "newPassword214!", "01012341234");
    }

    public static Member createMember2() {
        return new Member("mem2@email.com", "newPassword214!", "01012341234");
    }
}
