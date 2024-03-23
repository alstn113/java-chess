package chess.domain.state;

import chess.domain.Board;
import chess.domain.Color;
import chess.domain.position.Position;

public class BlackState extends RunningState {
    @Override
    public GameState move(Board board, Position source, Position target) {
        board.move(source, target, Color.BLACK);

        return new WhiteState();
    }

    @Override
    public GameState status() {
        return new StatusState(Color.BLACK);
    }
}
