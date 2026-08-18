ALTER TABLE application
ADD COLUMN created_at TIMESTAMP,
ADD COLUMN created_by TEXT,
ADD COLUMN modified_at TIMESTAMP,
ADD COLUMN modified_by TEXT;

ALTER TABLE application
ALTER COLUMN job_id SET NOT NULL;

ALTER TABLE application
ALTER COLUMN candidate_id SET NOT NULL;

ALTER TABLE application
ADD CONSTRAINT uk_application_job_candidate
UNIQUE (job_id, candidate_id);
