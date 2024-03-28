package chess.dao;

import chess.database.DBConnectionUtil;
import chess.dto.ChessGameRequest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ChessGameDaoImpl implements ChessGameDao {

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
}
