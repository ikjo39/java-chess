package chess.dao;

import chess.dto.ChessBoardDto;
import chess.dto.PieceDto;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class BoardDao implements BoardRepository {
    @Override
    public void add(ChessBoardDto chessBoardDto) {
        String chessBoardQuery = "INSERT INTO chess_board (position, type, turn) VALUES (?, ?, ?)";
        try (final var connection = CommonDao.getConnection()) {
            assert connection != null;
            try (final var preparedStatement = connection.prepareStatement(chessBoardQuery)) {
                for (PieceDto piece : chessBoardDto.pieces()) {
                    preparedStatement.setString(1, piece.position());
                    preparedStatement.setString(2, piece.type());
                    preparedStatement.setString(3, chessBoardDto.turn());
                    preparedStatement.addBatch();
                }
                preparedStatement.executeBatch();
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
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

    @Override
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
                String turn = null;
                while (resultSet.next()) {
                    if (turn == null) {
                        turn = resultSet.getString("turn");
                    }
                    String position = resultSet.getString("position");
                    String type = resultSet.getString("type");
                    pieces.add(PieceDto.from(position, type));
                }
                return ChessBoardDto.from(pieces, turn);
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
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

    @Override
    public void deleteAll() {
        final String boardQuery = "DELETE FROM chess_board;";
        try (final var connection = CommonDao.getConnection()) {
            assert connection != null;
            try (final var statement = connection.createStatement()) {
                statement.execute(boardQuery);
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
