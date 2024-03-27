package chess;

import chess.controller.ChessController;
import chess.domain.board.BoardFactory;
import chess.domain.ChessGame;

public class Application {
    public static void main(String[] args) {
        ChessGame chessGame = new ChessGame(BoardFactory.createInitialBoard());
        ChessController chessController = new ChessController(chessGame);

        chessController.run();
    }
}
