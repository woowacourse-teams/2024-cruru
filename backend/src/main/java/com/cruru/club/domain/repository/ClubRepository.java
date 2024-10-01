package com.cruru.club.domain.repository;

import com.cruru.club.domain.Club;
import com.cruru.member.domain.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ClubRepository extends JpaRepository<Club, Long> {

    Optional<Club> findByMember(Member member);

    @Query("""
           SELECT c FROM Club c 
           JOIN FETCH c.member 
           WHERE c.id = :id"""
    )
    Optional<Club> findByIdFetchingMember(@Param("id") long id);
}
