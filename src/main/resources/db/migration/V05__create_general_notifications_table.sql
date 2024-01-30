BEGIN;

CREATE TABLE general_notifications
(
    id       BIGINT         NOT NULL AUTO_INCREMENT,
    date     DATE           NOT NULL,
    title    VARCHAR(2000)  NOT NULL,
    content  VARCHAR(10000) NOT NULL,
    hash     BINARY(16) NOT NULL,
    PRIMARY KEY (id)
);

CREATE INDEX general_notifications_hash_idx ON general_notifications (hash);

COMMIT;