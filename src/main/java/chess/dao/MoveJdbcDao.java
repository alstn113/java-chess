package chess.dao;

import chess.database.DBConnectionUtil;
import chess.dto.MoveRequest;
import chess.dto.MoveResponse;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MoveJdbcDao implements MoveDao {
    @Override
    public Long save(MoveRequest moveRequest) {
        String query = "INSERT INTO move (source, target, chess_game_id) VALUES (?, ?, ?)";

        try (Connection connection = DBConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query,
                     Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, moveRequest.source());
            preparedStatement.setString(2, moveRequest.target());
            preparedStatement.setLong(3, moveRequest.chessGameId());
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                return resultSet.getLong(1);
            }

            return -1L;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveAll(List<MoveRequest> moveRequests) {
        String query = "INSERT INTO move (source, target, chess_game_id) VALUES (?, ?, ?)";

        try (Connection connection = DBConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            for (MoveRequest moveRequest : moveRequests) {
                preparedStatement.setString(1, moveRequest.source());
                preparedStatement.setString(2, moveRequest.target());
                preparedStatement.setLong(3, moveRequest.chessGameId());
                preparedStatement.addBatch();
            }

            preparedStatement.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<MoveResponse> findAll(Long chessGameId) {
        String query = "SELECT * FROM move WHERE chess_game_id = ?";

        try (Connection connection = DBConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.setLong(1, chessGameId);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<MoveResponse> pieceResponses = new ArrayList<>();
            while (resultSet.next()) {
                pieceResponses.add(new MoveResponse(
                        resultSet.getLong("id"),
                        resultSet.getString("source"),
                        resultSet.getString("target"),
                        resultSet.getLong("chess_game_id")
                ));
            }
            return pieceResponses;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteAll() {
        String query = "DELETE FROM move";

        try (Connection connection = DBConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
