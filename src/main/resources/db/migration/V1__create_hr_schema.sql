CREATE TABLE job (
    id BIGSERIAL PRIMARY KEY,
    title TEXT,
    description TEXT,
    department TEXT,
    status TEXT,
    created_at TIMESTAMP,
    created_by TEXT,
    modified_at TIMESTAMP,
    modified_by TEXT,
    assigned_hr TEXT
);

CREATE TABLE candidate (
    id BIGSERIAL PRIMARY KEY,
    first_name TEXT,
    last_name TEXT,
    email TEXT UNIQUE,
    phone TEXT,
    linkedin_url TEXT,
    address TEXT,
    experience TEXT,
    soft_skills TEXT,
    technical_skills TEXT,
    summary TEXT,
    education TEXT,
    created_at TIMESTAMP,
    created_by TEXT,
    modified_at TIMESTAMP,
    modified_by TEXT,
    password TEXT
);


CREATE TABLE application (
    id BIGSERIAL PRIMARY KEY,
    job_id BIGINT,
    candidate_id BIGINT,
    status TEXT,

    CONSTRAINT fk_application_job
        FOREIGN KEY (job_id)
        REFERENCES job(id),

    CONSTRAINT fk_application_candidate
        FOREIGN KEY (candidate_id)
        REFERENCES candidate(id)
);