package chess.domain.state;

public abstract class RunningState implements GameState {
    @Override
    public GameState start() {
        throw new UnsupportedOperationException("이미 시작한 상태입니다.");
    }

    @Override
    public GameState end() {
        return new EndState();
    }

    @Override
    public GameState status() {
        return this;
    }

    @Override
    public boolean isPlaying() {
        return true;
    }
}
