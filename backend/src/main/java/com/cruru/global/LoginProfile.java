package com.cruru.global;

import com.cruru.member.domain.MemberRole;

public record LoginProfile(String email, MemberRole memberRole) {

}
