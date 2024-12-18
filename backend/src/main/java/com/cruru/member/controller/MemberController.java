package com.cruru.member.controller;

import com.cruru.auth.annotation.RequireAuth;
import com.cruru.member.controller.request.EmailChangeRequest;
import com.cruru.member.controller.request.MemberCreateRequest;
import com.cruru.member.domain.Member;
import com.cruru.member.facade.MemberFacade;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberFacade memberFacade;

    @PostMapping("/signup")
    public ResponseEntity<Void> create(@RequestBody @Valid MemberCreateRequest request) {
        long memberId = memberFacade.create(request);
        return ResponseEntity.created(URI.create("/v1/members/" + memberId)).build();
    }

    @PatchMapping("/{memberId}/email")
    public ResponseEntity<Void> changeEmail(
            @RequireAuth(targetDomain = Member.class) @PathVariable Long memberId,
            @RequestBody @Valid EmailChangeRequest request) {
        memberFacade.changeEmail(request, memberId);
        return ResponseEntity.ok().build();
    }
}
