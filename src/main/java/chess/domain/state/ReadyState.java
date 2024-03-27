package chess.domain.state;

import chess.domain.board.Board;
import chess.domain.Color;
import chess.domain.position.Position;

public class ReadyState implements GameState {
    @Override
    public GameState start() {
        return new MoveState(Color.WHITE);
    }

    @Override
    public GameState move(Board board, Position source, Position target) {
        throw new UnsupportedOperationException("준비 상태에서는 움직일 수 없습니다.");
    }

    @Override
    public GameState end() {
        return new EndState();
    }

    @Override
    public GameState status() {
        throw new UnsupportedOperationException("준비 상태에서는 상태를 볼 수 없습니다.");
    }

    @Override
    public boolean isPlaying() {
        return true;
    }
}
