package com.cruru.club.service;

import com.cruru.club.controller.request.ClubCreateRequest;
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

    public Club findById(Long id) {
        return clubRepository.findById(id)
                .orElseThrow(ClubNotFoundException::new);
    }

    public Club findByIdFetchingMember(Long id) {
        return clubRepository.findByIdFetchingMember(id)
                .orElseThrow(ClubNotFoundException::new);
    }

    public Club findByMember(Member member) {
        return clubRepository.findByMember(member)
                .orElseThrow(ClubNotFoundException::new);
    }
}
