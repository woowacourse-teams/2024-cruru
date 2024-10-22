ALTER TABLE refresh_token
    DROP CONSTRAINT fk_refresh_token_to_member;
ALTER TABLE refresh_token
    DROP CONSTRAINT uk_refresh_token_member_id;
ALTER TABLE refresh_token
    DROP CONSTRAINT uk_refresh_token_token;

DROP TABLE IF EXISTS refresh_token;
