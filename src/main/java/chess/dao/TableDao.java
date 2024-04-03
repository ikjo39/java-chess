package chess.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class TableDao {
    private TableDao() {
    }

    public static void createChessBoardIfNotExist() {
        final String query = """
                CREATE TABLE IF NOT EXISTS chess_board (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    `position` VARCHAR(2) UNIQUE,
                    `type` VARCHAR(1),
                    `turn` VARCHAR(50)
                );
                """;
        try (final Connection connection = CommonDao.getConnection();
             final Statement statement = connection.createStatement()) {
            statement.execute(query);
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
