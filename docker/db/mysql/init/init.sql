USE chess;

CREATE TABLE chess_game (
                            name VARCHAR(255) PRIMARY KEY
);

CREATE TABLE move (
                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                      source VARCHAR(2) NOT NULL,
                      target VARCHAR(2) NOT NULL,
                      chess_game_name VARCHAR(255) NOT NULL,
                      FOREIGN KEY (chess_game_name) REFERENCES chess_game(name)
);
