package com.cruru.applicant.service;

import com.cruru.applicant.controller.dto.ApplicantMoveRequest;
import com.cruru.applicant.controller.dto.ApplicantUpdateRequest;
import com.cruru.applicant.domain.Applicant;
import com.cruru.applicant.domain.repository.ApplicantRepository;
import com.cruru.applicant.exception.ApplicantNotFoundException;
import com.cruru.applicant.exception.badrequest.ApplicantNoChangeException;
import com.cruru.applicant.exception.badrequest.ApplicantRejectException;
import com.cruru.process.domain.Process;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ApplicantService {

    private final ApplicantRepository applicantRepository;

    public List<Applicant> findAllByProcess(Process process) {
        return applicantRepository.findAllByProcess(process);
    }

    public Applicant findById(long applicantId) {
        return applicantRepository.findById(applicantId)
                .orElseThrow(ApplicantNotFoundException::new);
    }

    @Transactional
    public void updateApplicantInformation(ApplicantUpdateRequest request, Applicant applicant) {
        if (applicant.getName().equals(request.name())
                && applicant.getEmail().equals(request.email())
                && applicant.getPhone().equals(request.phone())) {
            throw new ApplicantNoChangeException();
        }
        applicant.updateInfo(request.name(), request.email(), request.phone());
    }

    @Transactional
    public void moveApplicantProcess(Process process, ApplicantMoveRequest moveRequest) {
        List<Applicant> applicants = applicantRepository.findAllById(moveRequest.applicantIds());
        applicants.forEach(applicant -> applicant.updateProcess(process));
    }

    @Transactional
    public void reject(long applicantId) {
        Applicant applicant = findById(applicantId);
        validateRejectable(applicant);
        applicant.reject();
    }

    private void validateRejectable(Applicant applicant) {
        if (applicant.isRejected()) {
            throw new ApplicantRejectException();
        }
    }
}
