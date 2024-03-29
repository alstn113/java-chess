package chess;

import chess.controller.ChessController;
import chess.dao.ChessGameJdbcDao;
import chess.dao.MoveJdbcDao;
import chess.domain.ChessGame;
import chess.domain.board.BoardFactory;
import chess.service.ChessGameService;

public class Application {
    public static void main(String[] args) {
        ChessGame chessGame = new ChessGame(BoardFactory.createInitialBoard());
        ChessGameService chessGameService = new ChessGameService(
                new ChessGameJdbcDao(),
                new MoveJdbcDao()
        );
        ChessController chessController = new ChessController(chessGame, chessGameService);

        chessController.run();
    }
}
