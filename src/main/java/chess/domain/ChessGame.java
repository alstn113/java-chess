package chess.domain;

import chess.domain.board.Board;
import chess.domain.position.Position;
import chess.domain.state.GameState;
import chess.domain.state.ReadyState;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChessGame {
    private final Board board;
    private GameState gameState;
    private final List<Move> moveHistory;

    public ChessGame(Board board) {
        this.board = board;
        this.gameState = new ReadyState();
        this.moveHistory = new ArrayList<>();
    }

    public void start(List<Move> moves) {
        this.gameState = gameState.start();

        load(moves);
    }

    public void move(Position source, Position target) {
        this.gameState = gameState.move(board, source, target);
    }

    public void moveAndRecord(Position source, Position target) {
        move(source, target);
        moveHistory.add(new Move(source, target));
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

    public boolean isKingDead() {
        return board.isKingDead();
    }

    public List<Move> getMoveHistory() {
        return Collections.unmodifiableList(moveHistory);
    }

    public Board getBoard() {
        return board;
    }
}
