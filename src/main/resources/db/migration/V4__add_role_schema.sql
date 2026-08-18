CREATE TABLE role (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

INSERT INTO role (name) VALUES ('CANDIDATE');
INSERT INTO role (name) VALUES ('HR');
INSERT INTO role (name) VALUES ('ADMIN');

ALTER TABLE candidate
ADD COLUMN role_id BIGINT;

ALTER TABLE candidate
ADD CONSTRAINT fk_candidate_role
FOREIGN KEY (role_id)
REFERENCES role(id);

UPDATE candidate
SET role_id = (
    SELECT id
    FROM role
    WHERE name = 'CANDIDATE'
);