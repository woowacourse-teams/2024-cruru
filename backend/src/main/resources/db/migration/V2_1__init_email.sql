CREATE TABLE email
(
    email_id     BIGINT NOT NULL AUTO_INCREMENT,
    applicant_id BIGINT,
    club_id      BIGINT,
    created_date DATETIME(6),
    updated_date DATETIME(6),
    subject      VARCHAR(1023),
    content         TEXT,
    PRIMARY KEY (notice_id)
) ENGINE=InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

ALTER TABLE email
    ADD CONSTRAINT fk_email_to_club
        FOREIGN KEY (club_id)
            REFERENCES club (club_id)

ALTER TABLE email
    ADD CONSTRAINT fk_email_to_applicant
        FOREIGN KEY (applicant_id)
            REFERENCES applicant (applicant_id)
