package chess.dao;

import chess.database.DBConnectionUtil;
import chess.dto.ChessGameRequest;
import chess.dto.ChessGameResponse;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class ChessGameJdbcDao implements ChessGameDao {

    @Override
    public void save(ChessGameRequest chessGameRequest) {
        String query = "INSERT INTO chess_game (game_status) VALUES (?)";

        try (Connection connection = DBConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, chessGameRequest.gameStatus());
            preparedStatement.executeUpdate();
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
}
