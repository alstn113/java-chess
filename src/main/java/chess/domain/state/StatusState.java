package chess.domain.state;

import chess.domain.Board;
import chess.domain.Color;
import chess.domain.position.Position;

public class StatusState extends RunningState {
    private final Color color;

    public StatusState(Color color) {
        this.color = color;
    }

    @Override
    public GameState move(Board board, Position source, Position target) {
        board.move(source, target, color);

        if (color.isWhite()) {
            return new BlackState();
        }

        return new WhiteState();
    }

    @Override
    public GameState status() {
        return new StatusState(color);
    }
}
