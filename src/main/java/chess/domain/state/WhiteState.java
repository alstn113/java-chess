package chess.domain.state;

import chess.domain.Board;
import chess.domain.Color;
import chess.domain.position.Position;

public class WhiteState extends RunningState {
    @Override
    public GameState move(Board board, Position source, Position target) {
        board.move(source, target, Color.WHITE);

        return new BlackState();
    }

    @Override
    public GameState status() {
        return new StatusState(Color.WHITE);
    }
}
