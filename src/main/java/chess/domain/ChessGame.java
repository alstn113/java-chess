package chess.domain;

import chess.domain.position.Position;
import chess.domain.state.GameState;
import chess.domain.state.ReadyState;

public class ChessGame {
    private final Board board;
    private GameState gameState;

    public ChessGame(Board board) {
        this.board = board;
        this.gameState = new ReadyState();
    }

    public void start() {
        this.gameState = gameState.start();
    }

    public void move(String source, String target) {
        Position sourcePosition = Position.convert(source);
        Position targetPosition = Position.convert(target);

        this.gameState = gameState.move(board, sourcePosition, targetPosition);
    }

    public void end() {
        this.gameState = gameState.end();
    }

    public ChessGameStatus status() {
        double whiteScore = board.calculateScore(Color.WHITE);
        double blackScore = board.calculateScore(Color.BLACK);
        ChessGameStatus chessGameStatus = new ChessGameStatus(whiteScore, blackScore);

        this.gameState = gameState.status();

        return chessGameStatus;
    }

    public boolean isPlaying() {
        return gameState.isPlaying();
    }

    public Board getBoard() {
        return board;
    }
}
