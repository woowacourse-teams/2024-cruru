package com.cruru.auth.controller.dto;

import com.cruru.member.domain.MemberRole;

public record LoginProfile(String email, MemberRole memberRole) {

}
