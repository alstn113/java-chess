package chess.dao;

import chess.dto.ChessGameRequest;
import chess.dto.ChessGameResponse;
import java.util.Optional;

public interface ChessGameDao {
    void save(ChessGameRequest chessGameRequest);

    Optional<ChessGameResponse> findRecentPlayingGame();
}
