package chess.dao;

import chess.dto.ChessGameRequest;
import chess.dto.ChessGameResponse;
import chess.dto.ChessGameUpdateRequest;
import java.util.Optional;

public interface ChessGameDao {
    Long save(ChessGameRequest chessGameRequest);

    Optional<ChessGameResponse> findRecentPlayingGame();

    void updateGameStatus(ChessGameUpdateRequest chessGameUpdateRequest);
}
