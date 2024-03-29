package chess.service;

import chess.dao.ChessGameDao;
import chess.dao.MoveDao;
import chess.domain.GameStatus;
import chess.domain.Move;
import chess.dto.ChessGameRequest;
import chess.dto.ChessGameResponse;
import chess.dto.MoveResponse;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ChessGameService {
    private final ChessGameDao chessGameDao;
    private final MoveDao moveDao;

    public ChessGameService(ChessGameDao chessGameDao, MoveDao moveDao) {
        this.chessGameDao = chessGameDao;
        this.moveDao = moveDao;
    }

    public void save(GameStatus gameStatus) {
        ChessGameRequest chessGameRequest = ChessGameRequest.from(gameStatus);
        chessGameDao.save(chessGameRequest);
    }

    public List<Move> getRecentPlayingGameMoves() {
        Optional<ChessGameResponse> recentPlayingGame = getRecentPlayingGame();

        return recentPlayingGame.map(ChessGameResponse::id)
                .map(this::getMovesByChessGameId)
                .orElse(Collections.emptyList());
    }

    private Optional<ChessGameResponse> getRecentPlayingGame() {
        return chessGameDao.findRecentPlayingGame();
    }

    private List<Move> getMovesByChessGameId(Long chessGameId) {
        return moveDao.findAll(chessGameId).stream()
                .map(MoveResponse::from)
                .toList();
    }
}
