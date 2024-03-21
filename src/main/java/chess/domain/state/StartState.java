package chess.domain.state;

import chess.domain.Color;

public class StartState implements GameState {
    private static final Color START_COLOR = Color.WHITE;

    @Override
    public GameState start() {
        throw new UnsupportedOperationException("이미 시작 상태입니다.");
    }

    @Override
    public GameState move() {
        return new MoveState(START_COLOR);
    }

    @Override
    public GameState end() {
        return new EndState();
    }

    @Override
    public boolean isPlaying() {
        return true;
    }
}
