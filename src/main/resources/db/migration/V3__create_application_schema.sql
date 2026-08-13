CREATE TABLE application (
    id BIGSERIAL PRIMARY KEY,
    job_id BIGINT NOT NULL,
    candidate_id BIGINT NOT NULL,
    status TEXT,

    created_at TIMESTAMP,
    created_by TEXT,
    modified_at TIMESTAMP,
    modified_by TEXT,

    CONSTRAINT fk_application_job
        FOREIGN KEY (job_id)
        REFERENCES job(id),

    CONSTRAINT fk_application_candidate
        FOREIGN KEY (candidate_id)
        REFERENCES candidate(id),

    CONSTRAINT uk_application_job_candidate
        UNIQUE (job_id, candidate_id)
);