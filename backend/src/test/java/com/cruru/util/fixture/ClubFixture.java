package com.cruru.util.fixture;

import com.cruru.club.domain.Club;

public class ClubFixture {

    public static Club createClub() {
        return new Club("크루루", null);
    }
}
