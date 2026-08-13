CREATE TABLE experience (
    id BIGSERIAL PRIMARY KEY,
    title TEXT,
    description TEXT,
    candidate_id BIGINT NOT NULL,

    CONSTRAINT fk_experience_candidate
        FOREIGN KEY (candidate_id)
        REFERENCES candidate(id)
    ALTER TABLE candidate
    DROP COLUMN experience;
);
