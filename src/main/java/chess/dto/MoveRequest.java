package chess.dto;

import chess.domain.position.Position;

public record MoveRequest(String source, String target, Long chessGameId) {
    public static MoveRequest of(Position source, Position target, Long chessGameId) {
        return new MoveRequest(source.getValue(), target.getValue(), chessGameId);
    }
}
