package com.cruru.auth.domain.repository;

import com.cruru.auth.domain.RefreshToken;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, Long> {

    boolean existsByToken(String token);

    boolean existsByMemberId(Long memberId);

    Optional<RefreshToken> findByMemberId(Long memberId);
}
