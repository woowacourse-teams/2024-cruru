package com.cruru.email.domain.repository;

import com.cruru.applicant.domain.Applicant;
import com.cruru.email.domain.Email;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface EmailRepository extends JpaRepository<Email, Long> {

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query("DELETE FROM Email e WHERE e.to IN :tos")
    void deleteAllByTos(@Param("tos") List<Applicant> tos);
}
