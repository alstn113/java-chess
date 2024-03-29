package chess.dao;

import static org.assertj.core.api.Assertions.assertThat;

import chess.domain.GameStatus;
import chess.dto.ChessGameRequest;
import chess.dto.ChessGameResponse;
import chess.dto.ChessGameUpdateRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ChessGameJdbcDaoTest {
    private final ChessGameDao chessGameDao = new ChessGameJdbcDao();
    private final MoveDao moveDao = new MoveJdbcDao();

    @AfterEach
    void setUp() {
        moveDao.deleteAll();
        chessGameDao.deleteAll();
    }

    @DisplayName("체스 게임을 저장할 수 있다.")
    @Test
    void save() {
        ChessGameRequest chessGameRequest = ChessGameRequest.from(GameStatus.FINISHED);
        Long id = chessGameDao.save(chessGameRequest);

        assertThat(id).isNotNull();
    }

    @DisplayName("최근 플레이 중인 체스 게임을 조회할 수 있다.")
    @Test
    void findRecentPlayingGame() {
        ChessGameRequest chessGameRequest = ChessGameRequest.from(GameStatus.PLAYING);
        Long id = chessGameDao.save(chessGameRequest);

        ChessGameResponse chessGameResponse = new ChessGameResponse(id, "PLAYING");
        assertThat(chessGameDao.findRecentPlayingGame()).hasValue(chessGameResponse);
    }

    @DisplayName("게임 상태를 업데이트할 수 있다.")
    @Test
    void updateGameStatus() {
        ChessGameRequest chessGameRequest = ChessGameRequest.from(GameStatus.FINISHED);
        Long id = chessGameDao.save(chessGameRequest);

        ChessGameUpdateRequest chessGameUpdateRequest = new ChessGameUpdateRequest(id, "PLAYING");
        chessGameDao.updateGameStatus(chessGameUpdateRequest);

        ChessGameResponse updatedChessGameResponse = new ChessGameResponse(id, "PLAYING");
        assertThat(chessGameDao.findRecentPlayingGame()).hasValue(updatedChessGameResponse);
    }
}
