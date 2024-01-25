BEGIN;

CREATE TABLE subscriber_types (
    id VARCHAR(36) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE subscribers (
    id INT NOT NULL AUTO_INCREMENT,
    code VARCHAR(255) NOT NULL,
    type_id VARCHAR(36) NOT NULL,
    additional_info VARCHAR(255) DEFAULT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY (type_id, code),
    FOREIGN KEY (type_id) REFERENCES subscriber_types (id) ON DELETE RESTRICT ON UPDATE RESTRICT
);

COMMIT;