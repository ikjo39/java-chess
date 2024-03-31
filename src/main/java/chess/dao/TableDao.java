package chess.dao;

import java.sql.SQLException;

public class TableDao {
    private TableDao() {
    }

    public static boolean createChessBoardIfNotExist() {
        final String query = """
                CREATE TABLE IF NOT EXISTS chess_board (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    `position` VARCHAR(2) UNIQUE,
                    `type` VARCHAR(1)
                );
                """;
        try (final var connection = CommonDao.getConnection()) {
            assert connection != null;
            try (final var statement = connection.createStatement()) {
                return statement.execute(query);
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
