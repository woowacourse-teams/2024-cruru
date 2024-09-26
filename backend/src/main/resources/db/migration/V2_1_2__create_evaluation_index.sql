CREATE INDEX idx_applicant_id_and_process_id ON evaluation (applicant_id, process_id);
CREATE INDEX idx_applicant_id_and_evaluation_id_and_score ON evaluation (applicant_id, evaluation_id, score);
