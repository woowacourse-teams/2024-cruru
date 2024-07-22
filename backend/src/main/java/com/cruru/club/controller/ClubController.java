package com.cruru.club.controller;

import com.cruru.club.controller.dto.ClubCreateRequest;
import com.cruru.club.service.ClubService;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/clubs")
@RequiredArgsConstructor
public class ClubController {

    private final ClubService clubService;

    @PostMapping
    public ResponseEntity<Void> create(
            @RequestBody @Valid ClubCreateRequest request,
            @RequestParam(name = "member_id") Long memberId
    ) {
        long clubId = clubService.create(request, memberId);
        return ResponseEntity.created(URI.create("/v1/clubs/" + clubId)).build();
    }
}
