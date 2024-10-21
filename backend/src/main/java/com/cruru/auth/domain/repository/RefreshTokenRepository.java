package com.cruru.auth.domain.repository;

import com.cruru.auth.domain.RefreshToken;
import com.cruru.member.domain.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    boolean existsByToken(String token);

    boolean existsByMember(Member member);

    Optional<RefreshToken> findByMember(Member member);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE RefreshToken rt SET rt.token = :refreshToken WHERE rt.member = :member")
    void updateRefreshTokenByMember(@Param("refreshToken") String refreshToken, @Param("member") Member member);
}
