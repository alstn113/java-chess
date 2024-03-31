package chess;

import chess.controller.ChessController;
import chess.dao.MoveJdbcDao;
import chess.domain.game.ChessGame;
import chess.domain.board.BoardFactory;
import chess.service.ChessGameService;

public class Application {
    public static void main(String[] args) {
        ChessGameService chessGameService = new ChessGameService(new MoveJdbcDao());
        ChessGame chessGame = new ChessGame(BoardFactory.createInitialBoard());
        ChessController chessController = new ChessController(chessGame, chessGameService);

        chessController.run();
    }
}
