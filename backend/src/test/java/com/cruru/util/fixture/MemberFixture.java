package com.cruru.util.fixture;

import com.cruru.member.domain.Member;
import com.cruru.member.domain.MemberRole;

public class MemberFixture {

    public static final Member ADMIN = new Member(1000000L, "admin@email.com",
            "$2a$10$rG0JsflKdGcORjGFTURYb.npEgtvClK4.3P.EMr/o3SdekrVFxOvG", "01011111111",
            MemberRole.ADMIN); // password 원문: qwer1234
    public static final Member DOBBY = new Member("dobby@email.com",
            "$2a$10$RKBwn5Sa7EO0lYaZqU1zSupNbPJ5/HOKcI7gNb9c2q.TiydBHUBQK",
            "01011111111"); // password 원문: newPassword214!
    public static final Member RUSH = new Member("rush@email.com",
            "$2a$10$RKBwn5Sa7EO0lYaZqU1zSupNbPJ5/HOKcI7gNb9c2q.TiydBHUBQK",
            "01022222222"); // password 원문: newPassword214!
}
