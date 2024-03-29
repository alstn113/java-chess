package chess.dao;

import chess.database.DBConnectionUtil;
import chess.dto.ChessGameRequest;
import chess.dto.ChessGameResponse;
import chess.dto.ChessGameUpdateRequest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class ChessGameJdbcDao implements ChessGameDao {

    @Override
    public Long save(ChessGameRequest chessGameRequest) {
        String query = "INSERT INTO chess_game (game_status) VALUES (?)";

        try (Connection connection = DBConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query,
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, chessGameRequest.gameStatus());
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
    public Optional<ChessGameResponse> findRecentPlayingGame() {
        String query = "SELECT * FROM chess_game WHERE game_status = ? ORDER BY id DESC LIMIT 1";

        try (Connection connection = DBConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {

            preparedStatement.setString(1, "PLAYING");
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(new ChessGameResponse(
                        resultSet.getLong("id"),
                        resultSet.getString("game_status")));
            }

            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateGameStatus(ChessGameUpdateRequest chessGameUpdateRequest) {
        String query = "UPDATE chess_game SET game_status = ? WHERE id = ?";

        try (Connection connection = DBConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.setString(1, chessGameUpdateRequest.gameStatus());
            preparedStatement.setLong(2, chessGameUpdateRequest.id());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteAll() {
        String query = "DELETE FROM chess_game";

        try (Connection connection = DBConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
