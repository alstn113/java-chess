USE chess;

CREATE TABLE move (
                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                      source VARCHAR(2) NOT NULL,
                      target VARCHAR(2) NOT NULL
);
