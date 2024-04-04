package chess.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class TableDao {
    private TableDao() {
    }

    public static void createChessGameIfNotExist() {
        final String query = """
                CREATE TABLE IF NOT EXISTS chess_game (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    `position` VARCHAR(2) UNIQUE,
                    `piece_type` VARCHAR(1),
                    `turn` VARCHAR(50)
                );
                """;
        try (final Connection connection = CommonDao.getConnection();
             final Statement statement = connection.createStatement()) {
            statement.execute(query);
        } catch (final SQLException e) {
            throw new RuntimeException("테이블 생성 과정에서 오류가 생겨 해당 명령을 수행할 수 없습니다.");
        }
    }
}
