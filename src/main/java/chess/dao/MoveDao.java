package chess.dao;

import chess.dto.MoveRequest;
import chess.dto.MoveResponse;
import java.util.List;

public interface MoveDao {
    Long save(MoveRequest moveRequest);

    void saveAll(List<MoveRequest> moveRequests);

    List<MoveResponse> findAll(Long chessGameId);

    void deleteAll();
}
