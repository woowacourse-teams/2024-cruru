package com.cruru.util.fixture;

import com.cruru.applicant.domain.Applicant;
import com.cruru.process.domain.Process;

public class ApplicantFixture {

    public static Applicant pendingDobby() {
        return new Applicant("도비", "DOBBY@email.com", "01000000000", null);
    }

    public static Applicant pendingDobby(Process process) {
        return new Applicant("도비", "DOBBY@email.com", "01000000000", process);
    }

    public static Applicant pendingRush() {
        return new Applicant("러쉬", "RUSH@email.com", "01000000001", null);
    }

    public static Applicant rejectedRush() {
        Applicant applicant = new Applicant("러쉬", "RUSH@email.com", "01000000001", null);
        applicant.reject();
        return applicant;
    }

    public static Applicant rejectedRush(Process process) {
        Applicant applicant = new Applicant("러쉬", "rush@email.com", "01000000001", process);
        applicant.reject();
        return applicant;
    }
}
