package chess.dao;

import chess.domain.Move;
import chess.domain.position.Position;
import chess.dto.MoveRequest;
import chess.dto.MoveResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MoveJdbcDaoTest {
    private final MoveJdbcDao moveJdbcDao = new MoveJdbcDao();

    @DisplayName("moveDao를 저장할 수 있다.")
    @Test
    void save() {
        Position source = Position.convert("a2");
        Position target = Position.convert("a4");

        moveJdbcDao.save(MoveRequest.of(source, target, 1L));
    }

    @DisplayName("moveDao를 조회할 수 있다.")
    @Test
    void findAll() {
        moveJdbcDao.findAll(1L).forEach(moveResponse -> {
            Move move = MoveResponse.from(moveResponse);
            System.out.println(move.source() + " " + move.target());
        });
    }
}
