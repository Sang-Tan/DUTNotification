BEGIN;

CREATE TABLE group_notifications (
    id BIGINT NOT NULL AUTO_INCREMENT,
    date DATE NOT NULL,
    group_id VARCHAR(50) NOT NULL,
    title VARCHAR(2000) NOT NULL,
    content VARCHAR(10000) NOT NULL,
    hash BINARY(16) NOT NULL,
    PRIMARY KEY (id)
);

CREATE INDEX group_notifications_hash_idx ON group_notifications (hash);

COMMIT;