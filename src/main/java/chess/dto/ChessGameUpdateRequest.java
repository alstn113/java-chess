package chess.dto;

import chess.domain.GameStatus;

public record ChessGameUpdateRequest(Long id, String gameStatus) {
    public static ChessGameUpdateRequest of(Long id, GameStatus gameStatus) {
        return new ChessGameUpdateRequest(id, gameStatus.name());
    }
}
