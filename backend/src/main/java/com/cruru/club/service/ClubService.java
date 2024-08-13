package com.cruru.club.service;

import com.cruru.club.controller.dto.ClubCreateRequest;
import com.cruru.club.domain.Club;
import com.cruru.club.domain.repository.ClubRepository;
import com.cruru.club.exception.ClubNotFoundException;
import com.cruru.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ClubService {

    private final ClubRepository clubRepository;

    @Transactional
    public Club create(ClubCreateRequest request, Member member) {
        return clubRepository.save(new Club(request.name(), member));
    }

    @Transactional
    public Club create(String name, Member member) {
        return clubRepository.save(new Club(name, member));
    }

    public Club findById(long id) {
        return clubRepository.findById(id)
                .orElseThrow(ClubNotFoundException::new);
    }
}
