package com.cruru.util.fixture;

import com.cruru.club.domain.Club;
import com.cruru.member.domain.Member;

public class ClubFixture {

    public static Club createClub() {
        return new Club("크루루", null);
    }

    public static Club createClub(Member member) {
        return new Club("크루루", member);
    }
}
