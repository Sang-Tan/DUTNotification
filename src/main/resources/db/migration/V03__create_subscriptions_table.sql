BEGIN;

CREATE TABLE subscriptions (
    subscriber_id INT NOT NULL,
    subject VARCHAR(50) NOT NULL,
    PRIMARY KEY (subject, subscriber_id),
    FOREIGN KEY (subscriber_id) REFERENCES subscribers (id) ON DELETE CASCADE ON UPDATE CASCADE
);

COMMIT;