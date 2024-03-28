package chess.dao;

import chess.database.DBConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ChessGameDaoImpl implements ChessGameDao {

    @Override
    public void save() {
        String query = "INSERT INTO chess_game () VALUES ()";

        try (Connection connection = DBConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
