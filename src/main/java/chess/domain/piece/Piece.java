package chess.domain.piece;

import chess.domain.Board;
import chess.domain.Color;
import chess.domain.Direction;
import chess.domain.position.Position;
import java.util.Set;

public abstract class Piece {
    private final Color color;

    protected Piece(Color color) {
        this.color = color;
    }

    public abstract Set<Position> calculateMovablePositions(Position currentPosition, Board board);

    public boolean isWhite() {
        return color.isWhite();
    }

    public abstract PieceType pieceType();

    protected abstract Set<Direction> directions();

    public boolean isEmpty() {
        return pieceType() == PieceType.NONE;
    }

    public boolean isNotSameColor(Piece piece) {
        return !color.isSame(piece.color);
    }

    public boolean isSameColor(Color other) {
        return color.isSame(other);
    }

    public Color getColor() {
        return color;
    }
}
