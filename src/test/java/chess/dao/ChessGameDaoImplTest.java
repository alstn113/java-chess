package chess.dao;

import chess.domain.GameStatus;
import chess.dto.ChessGameRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ChessGameDaoImplTest {
    private final ChessGameDaoImpl chessGameDaoImpl = new ChessGameDaoImpl();

    @DisplayName("체스 게임을 저장할 수 있다.")
    @Test
    void save() {
        ChessGameRequest chessGameRequest = ChessGameRequest.from(GameStatus.PLAYING);
        chessGameDaoImpl.save(chessGameRequest);
    }
}
