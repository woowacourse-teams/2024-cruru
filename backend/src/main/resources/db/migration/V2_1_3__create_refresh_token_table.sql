CREATE TABLE refresh_token
(
    refresh_token_id BIGINT        NOT NULL AUTO_INCREMENT,
    created_date     DATETIME(6),
    member_id        BIGINT        NOT NULL,
    updated_date     DATETIME(6),
    token            VARCHAR(512) NOT NULL,
    PRIMARY KEY (refresh_token_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
