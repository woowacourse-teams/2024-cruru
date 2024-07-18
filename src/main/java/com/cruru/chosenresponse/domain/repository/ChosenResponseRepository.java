package com.cruru.chosenresponse.domain.repository;

import com.cruru.chosenresponse.domain.ChosenResponse;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChosenResponseRepository extends JpaRepository<ChosenResponse, Long> {

    List<ChosenResponse> findAllByApplicantId(Long id);
}
