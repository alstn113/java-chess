package chess.service;

import chess.dao.ChessGameDao;
import chess.dao.MoveDao;
import chess.domain.GameStatus;
import chess.domain.Move;
import chess.dto.ChessGameRequest;
import chess.dto.ChessGameResponse;
import chess.dto.ChessGameUpdateRequest;
import chess.dto.MoveRequest;
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

    public Long addGame(GameStatus gameStatus) {
        ChessGameRequest chessGameRequest = ChessGameRequest.from(gameStatus);
        return chessGameDao.save(chessGameRequest);
    }

    public List<Move> getRecentPlayingGameMoves() {
        Optional<ChessGameResponse> recentPlayingGame = getRecentPlayingGame();

        return recentPlayingGame.map(ChessGameResponse::id)
                .map(this::getMovesByChessGameId)
                .orElse(Collections.emptyList());
    }

    public void addMovesToExistingOrNewPlayingGame(List<Move> moves, GameStatus gameStatus) {
        Optional<ChessGameResponse> recentPlayingGame = getRecentPlayingGame();

        if (recentPlayingGame.isPresent()) {
            Long existingGameId = recentPlayingGame.get().id();
            addMovesAndUpdateStatus(moves, gameStatus, existingGameId);
            return;
        }

        Long id = addGame(gameStatus);
        addMovesAndUpdateStatus(moves, gameStatus, id);
    }

    private void addMovesAndUpdateStatus(List<Move> moves, GameStatus gameStatus, Long id) {
        List<MoveRequest> moveRequests = moves.stream()
                .map(move -> MoveRequest.of(move.source(), move.target(), id))
                .toList();

        moveDao.saveAll(moveRequests);
        chessGameDao.updateGameStatus(ChessGameUpdateRequest.of(id, gameStatus));
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
