package com.cruru.applyform.domain.event;

import com.cruru.applicant.domain.Applicant;
import com.cruru.applyform.domain.ApplyForm;
import com.cruru.club.domain.Club;

public record ApplyFormEvent(Club club, ApplyForm applyForm, Applicant applicant) {
}
