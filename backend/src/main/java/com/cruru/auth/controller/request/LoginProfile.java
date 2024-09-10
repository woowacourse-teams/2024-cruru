package com.cruru.auth.controller.request;

import com.cruru.member.domain.MemberRole;

public record LoginProfile(String email, MemberRole memberRole) {

}
