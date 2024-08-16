package com.cruru.util.fixture;

import com.cruru.applicant.domain.Applicant;
import com.cruru.process.domain.Process;

public class ApplicantFixture {

    public static Applicant createPendingApplicantDobby() {
        return new Applicant("도비", "dobby@email.com", "01000000000", null);
    }

    public static Applicant createPendingApplicantDobby(Process process) {
        return new Applicant("도비", "dobby@email.com", "01000000000", process);
    }

    public static Applicant createPendingApplicantRush() {
        return new Applicant("러쉬", "rush@email.com", "01000000001", null);
    }

    public static Applicant createRejectedApplicantRush() {
        Applicant applicant = new Applicant("러쉬", "rush@email.com", "01000000001", null);
        applicant.reject();
        return applicant;
    }
}
