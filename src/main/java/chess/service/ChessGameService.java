package chess.service;

import chess.dao.ChessGameDao;
import chess.dao.MoveDao;
import chess.domain.GameStatus;
import chess.domain.Move;
import chess.dto.ChessGameRequest;
import chess.dto.ChessGameResponse;
import chess.dto.MoveResponse;
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

    public Optional<ChessGameResponse> getRecentPlayingGame() {
        return chessGameDao.findRecentPlayingGame();
    }

    public List<Move> getMovesByChessGameId(long chessGameId) {
        return moveDao.findAll(chessGameId).stream()
                .map(MoveResponse::from)
                .toList();
    }
}
