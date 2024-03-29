package chess.domain;

import chess.domain.board.Board;
import chess.domain.position.Position;
import chess.domain.state.GameState;
import chess.domain.state.ReadyState;
import java.util.List;

public class ChessGame {
    private final Board board;
    private GameState gameState;

    public ChessGame(Board board) {
        this.board = board;
        this.gameState = new ReadyState();
    }

    public void start(List<Move> moves) {
        this.gameState = gameState.start();

        load(moves);
    }

    public void move(Position source, Position target) {
        this.gameState = gameState.move(board, source, target);
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

    private void load(List<Move> moves) {
        moves.forEach(move -> {
            Position source = move.source();
            Position target = move.target();

            move(source, target);
        });
    }

    public boolean isPlaying() {
        return gameState.isPlaying();
    }

    public Board getBoard() {
        return board;
    }
}
