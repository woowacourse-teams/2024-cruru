ALTER TABLE refresh_token
    ADD CONSTRAINT uk_refresh_token_member_id UNIQUE (member_id);

ALTER TABLE refresh_token
    ADD CONSTRAINT uk_refresh_token_token UNIQUE (token);

ALTER TABLE refresh_token
    ADD CONSTRAINT fk_refresh_token_to_member
        FOREIGN KEY (member_id)
            REFERENCES member (member_id);
