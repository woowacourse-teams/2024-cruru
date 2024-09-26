package com.cruru.club.domain.repository;

import com.cruru.club.domain.Club;
import com.cruru.member.domain.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubRepository extends JpaRepository<Club, Long> {

    Optional<Club> findByMember(Member member);
}
