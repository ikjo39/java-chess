package chess.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Connection;
import java.sql.SQLException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CommonDaoTest {

    @Test
    @DisplayName("외부 DB와 커넥션을 가져온다.")
    void getConnection() throws SQLException {
        //when //then
        try (final Connection connection = CommonDao.getConnection()) {
            assertThat(connection).isNotNull();
        }
    }
}
