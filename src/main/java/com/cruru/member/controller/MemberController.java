package com.cruru.member.controller;

import com.cruru.member.controller.dto.MemberCreateRequest;
import com.cruru.member.controller.dto.MemberCreateResponse;
import com.cruru.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<MemberCreateResponse> create(@RequestBody MemberCreateRequest request) {
        memberService.create(request);
        return ResponseEntity.ok().build();
    }
}
