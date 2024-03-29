package chess.dao;

import chess.dto.MoveRequest;
import chess.dto.MoveResponse;
import java.util.List;

public interface MoveDao {
    Long save(MoveRequest moveRequest);

    List<MoveResponse> findAll(Long chessGameId);
}
