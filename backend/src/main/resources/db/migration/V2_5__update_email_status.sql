ALTER TABLE email CHANGE COLUMN is_succeed email_status VARCHAR(20) NOT NULL;

UPDATE email
SET email_status = CASE
                       WHEN email_status = '1' THEN 'DELIVERED'
                       WHEN email_status = '0' THEN 'FAILED'
                       ELSE 'PENDING'
    END;

ALTER TABLE email MODIFY COLUMN email_status VARCHAR(20) NOT NULL DEFAULT 'PENDING';
