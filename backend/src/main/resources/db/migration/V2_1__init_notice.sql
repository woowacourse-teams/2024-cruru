CREATE TABLE notice
(
    notice_id    BIGINT NOT NULL AUTO_INCREMENT,
    applicant_id BIGINT,
    club_id      BIGINT,
    created_date DATETIME(6),
    updated_date DATETIME(6),
    subject      VARCHAR(1023),
    text         TEXT,
    type         VARCHAR(255),
    PRIMARY KEY (notice_id)
) ENGINE=InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
