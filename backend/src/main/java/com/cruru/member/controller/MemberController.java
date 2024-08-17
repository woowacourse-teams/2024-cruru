package com.cruru.member.controller;

import com.cruru.global.util.CookieManager;
import com.cruru.member.controller.dto.LoginRequest;
import com.cruru.member.controller.dto.MemberCreateRequest;
import com.cruru.member.service.facade.MemberFacade;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberFacade memberFacade;
    private final CookieManager cookieManager;

    @PostMapping("/signup")
    public ResponseEntity<Void> create(@RequestBody @Valid MemberCreateRequest request) {
        long memberId = memberFacade.create(request);
        return ResponseEntity.created(URI.create("/v1/members/" + memberId)).build();
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody @Valid LoginRequest request) {
        String token = memberFacade.login(request);
        ResponseCookie cookie = cookieManager.createTokenCookie(token);
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .build();
    }
}
