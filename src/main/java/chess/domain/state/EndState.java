package chess.domain.state;

import chess.domain.board.Board;
import chess.domain.game.Color;
import chess.domain.position.Position;

public class EndState implements GameState {
    @Override
    public GameState start() {
        throw new UnsupportedOperationException("종료 상태에서는 시작할 수 없습니다.");
    }

    @Override
    public GameState move(Board board, Position source, Position target) {
        throw new UnsupportedOperationException("종료 상태에서는 움직일 수 없습니다.");
    }

    @Override
    public GameState end() {
        throw new UnsupportedOperationException("종료 상태에서는 종료할 수 없습니다.");
    }

    @Override
    public GameState status() {
        throw new UnsupportedOperationException("종료 상태에서는 상태를 볼 수 없습니다.");
    }

    @Override
    public boolean isPlaying() {
        return false;
    }

    @Override
    public Color getCurrentColor() {
        return Color.NONE;
    }
}
