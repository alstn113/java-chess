package chess.dao;

import chess.domain.GameStatus;
import chess.dto.ChessGameRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ChessGameJdbcDaoTest {
    private final ChessGameJdbcDao chessGameJdbcDao = new ChessGameJdbcDao();

    @DisplayName("체스 게임을 저장할 수 있다.")
    @Test
    void save() {
        ChessGameRequest chessGameRequest = ChessGameRequest.from(GameStatus.FINISHED);
        Long id = chessGameJdbcDao.save(chessGameRequest);
        System.out.println(id);
    }

    @DisplayName("최근 플레이 중인 체스 게임을 조회할 수 있다.")
    @Test
    void findRecentPlayingGame() {
        chessGameJdbcDao.findRecentPlayingGame().ifPresent(chessGameResponse -> {
            System.out.println(chessGameResponse);
        });
    }
}
