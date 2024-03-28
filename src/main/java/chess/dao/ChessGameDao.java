package chess.dao;

import chess.dto.ChessGameRequest;

public interface ChessGameDao {
    void save(ChessGameRequest chessGameRequest);
}
