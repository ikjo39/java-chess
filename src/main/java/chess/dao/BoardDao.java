package chess.dao;

import chess.dto.ChessBoardDto;
import chess.dto.PieceDto;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class BoardDao {
    public int[] add(ChessBoardDto chessBoardDto) {
        final String query = "INSERT INTO chess_board (`position`, `type`) VALUES(?, ?)";
        try (final var connection = CommonDao.getConnection()) {
            assert connection != null;
            try (final var preparedStatement = connection.prepareStatement(query)) {
                for (PieceDto piece : chessBoardDto.pieces()) {
                    preparedStatement.setString(1, piece.position());
                    preparedStatement.setString(2, piece.type());
                    preparedStatement.addBatch();
                }
                return preparedStatement.executeBatch();
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public PieceDto findByPosition(String data) {
        final String query = """
                SELECT *
                FROM chess_board 
                WHERE `position` = ?;
                """;
        try (final var connection = CommonDao.getConnection()) {
            assert connection != null;
            try (final var preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, data);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    String position = resultSet.getString("position");
                    String type = resultSet.getString("type");
                    return PieceDto.from(position, type);
                }
                throw new RuntimeException("데이터가 존재하지 않습니다.");
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ChessBoardDto findAll() {
        final String query = """
                SELECT * 
                FROM chess_board;
                """;
        try (final var connection = CommonDao.getConnection()) {
            assert connection != null;
            try (final var preparedStatement = connection.prepareStatement(query)) {
                Set<PieceDto> pieces = new HashSet<>();
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    String position = resultSet.getString("position");
                    String type = resultSet.getString("type");
                    pieces.add(PieceDto.from(position, type));
                }
                return ChessBoardDto.from(pieces);
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int put(PieceDto pieceDto) {
        final String query = """
                UPDATE chess_board 
                SET `type` = ? 
                WHERE `position` = ?;
                """;
        try (final var connection = CommonDao.getConnection()) {
            assert connection != null;
            try (final var preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, pieceDto.type());
                preparedStatement.setString(2, pieceDto.position());
                return preparedStatement.executeUpdate();
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int count() {
        final String query = "SELECT COUNT(*) AS `count` FROM chess_board;";
        try (final var connection = CommonDao.getConnection()) {
            assert connection != null;
            try (final var statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery(query);
                if (resultSet.next()) {
                    return resultSet.getInt("count");
                }
                throw new RuntimeException("데이터가 존재하지 않습니다.");
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteAll() {
        final String query = "TRUNCATE TABLE chess_board;";
        try (final var connection = CommonDao.getConnection()) {
            assert connection != null;
            try (final var statement = connection.createStatement()) {
                statement.execute(query);
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
