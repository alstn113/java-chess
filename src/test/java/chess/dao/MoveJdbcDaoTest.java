package chess.dao;

import static org.assertj.core.api.Assertions.assertThat;

import chess.domain.Move;
import chess.dto.ChessGameRequest;
import chess.dto.MoveRequest;
import chess.dto.MoveResponse;
import chess.fixture.PositionFixture;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MoveJdbcDaoTest {
    private final ChessGameDao chessGameDao = new ChessGameJdbcDao();
    private final MoveDao moveDao = new MoveJdbcDao();

    @AfterEach
    void rollback() {
        moveDao.deleteAll();
        chessGameDao.deleteAll();
    }

    @DisplayName("moveDao를 조회할 수 있다.")
    @Test
    void findAll() {
        Long id = chessGameDao.save(new ChessGameRequest("PLAYING"));

        moveDao.save(MoveRequest.of(PositionFixture.A2, PositionFixture.A4, id));
        moveDao.save(MoveRequest.of(PositionFixture.A7, PositionFixture.A5, id));

        List<MoveResponse> moveResponses = moveDao.findAll(id);

        List<Move> moves = moveResponses.stream()
                .map(MoveResponse::from)
                .toList();

        assertThat(moves).containsExactly(new Move(PositionFixture.A2, PositionFixture.A4),
                new Move(PositionFixture.A7, PositionFixture.A5));
    }
}
