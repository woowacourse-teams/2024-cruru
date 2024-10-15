package com.cruru.auth.controller;

import com.cruru.applyform.domain.ApplyForm;
import com.cruru.auth.annotation.RequireAuth;
import com.cruru.auth.annotation.ValidAuth;
import com.cruru.dashboard.domain.Dashboard;
import com.cruru.global.LoginProfile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth-test")
public class AuthTestController {

    @GetMapping("/test1")
    @ValidAuth
    public ResponseEntity<Void> readByRequestParam(
            @RequireAuth(targetDomain = ApplyForm.class) @RequestParam(name = "applyformId") Long applyFormId,
            LoginProfile loginProfile
    ) {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/test2/{targetId}")
    @ValidAuth
    public ResponseEntity<Void> readByPathVariable(
            @RequireAuth(targetDomain = ApplyForm.class) @PathVariable(name = "targetId") Long applyFormId,
            LoginProfile loginProfile
    ) {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/test3")
    @ValidAuth
    public ResponseEntity<Void> readByRequestBody(
            @RequestBody AuthTestDto authTestDto,
            LoginProfile loginProfile
    ) {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/test4/{targetId}")
    @ValidAuth
    public ResponseEntity<Void> readByAllRequestType(
            @RequireAuth(targetDomain = ApplyForm.class) @RequestParam(name = "applyformId") Long applyFormId,
            @RequireAuth(targetDomain = Dashboard.class) @PathVariable(name = "targetId") Long dashBoardId,
            @RequestBody AuthTestDto authTestDto,
            LoginProfile loginProfile
    ) {
        return ResponseEntity.ok().build();
    }
}
