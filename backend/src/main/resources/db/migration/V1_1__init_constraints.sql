ALTER TABLE answer
    ADD CONSTRAINT fk_answer_to_applicant
        FOREIGN KEY (applicant_id)
            REFERENCES applicant(applicant_id);

ALTER TABLE answer
    ADD CONSTRAINT fk_answer_to_question
        FOREIGN KEY (question_id)
            REFERENCES question(question_id);

ALTER TABLE applicant
    ADD CONSTRAINT fk_applicant_to_process
        FOREIGN KEY (process_id)
            REFERENCES process(process_id);

ALTER TABLE apply_form
    ADD CONSTRAINT fk_apply_form_to_dashboard
        FOREIGN KEY (dashboard_id)
            REFERENCES dashboard(dashboard_id);

ALTER TABLE choice
    ADD CONSTRAINT fk_choice_to_question
        FOREIGN KEY (question_id)
            REFERENCES question(question_id);

ALTER TABLE club
    ADD CONSTRAINT fk_club_to_member
        FOREIGN KEY (member_id)
            REFERENCES member(member_id);

ALTER TABLE dashboard
    ADD CONSTRAINT fk_dashboard_to_club
        FOREIGN KEY (club_id)
            REFERENCES club(club_id);

ALTER TABLE evaluation
    ADD CONSTRAINT fk_evaluation_to_applicant
        FOREIGN KEY (applicant_id)
            REFERENCES applicant(applicant_id);

ALTER TABLE evaluation
    ADD CONSTRAINT fk_evaluation_to_process
        FOREIGN KEY (process_id)
            REFERENCES process(process_id);

ALTER TABLE process
    ADD CONSTRAINT fk_process_to_dashboard
        FOREIGN KEY (dashboard_id)
            REFERENCES dashboard(dashboard_id);

ALTER TABLE question
    ADD CONSTRAINT fk_question_to_apply_form
        FOREIGN KEY (apply_form_id)
            REFERENCES apply_form(apply_form_id);
