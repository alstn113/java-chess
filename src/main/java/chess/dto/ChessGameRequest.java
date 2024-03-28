package chess.dto;

import chess.domain.GameStatus;

public record ChessGameRequest(String gameStatus) {
    public static ChessGameRequest from(GameStatus gameStatus) {
        return new ChessGameRequest(gameStatus.name());
    }
}
