USE chess;

CREATE TABLE chess_game (
    id INT AUTO_INCREMENT PRIMARY KEY,
    color VARCHAR(5) NOT NULL,
);

CREATE TABLE piece {
    id INT AUTO_INCREMENT PRIMARY KEY,
    game_id FOREIGN KEY REFERENCES chess_game(id),
    file VARCHAR(2) NOT NULL,
    rank VARCHAR(2) NOT NULL,
}
