ALTER TABLE email
    ADD CONSTRAINT fk_email_to_club
        FOREIGN KEY (club_id)
            REFERENCES club (club_id)

ALTER TABLE email
    ADD CONSTRAINT fk_email_to_applicant
        FOREIGN KEY (applicant_id)
            REFERENCES applicant (applicant_id)
