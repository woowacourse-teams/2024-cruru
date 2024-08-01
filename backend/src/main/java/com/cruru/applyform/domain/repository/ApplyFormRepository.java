package com.cruru.applyform.domain.repository;

import com.cruru.applyform.domain.ApplyForm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplyFormRepository extends JpaRepository<ApplyForm, Long> {

}
