package com.cruru.util.fixture;

import com.cruru.applicant.domain.Applicant;
import com.cruru.process.domain.Process;

public class ApplicantFixture {

    public static Applicant createApplicantDobby() {
        return new Applicant("도비", "dobby@email.com", "01000000000", null, false);
    }

    public static Applicant createApplicantDobby(Process process) {
        return new Applicant("도비", "dobby@email.com", "01000000000", process, false);
    }

    public static Applicant createApplicantRush() {
        return new Applicant("러쉬", "rush@email.com", "01000000001", null, false);
    }

    public static Applicant createApplicantRush(Process process) {
        return new Applicant("러쉬", "rush@email.com", "01000000001", process, false);
    }

    public static Applicant createRejectedApplicantLurgi() {
        return new Applicant("러기", "rush@email.com", "01000000001", null, true);
    }
}
