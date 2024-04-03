package chess.dao;

import chess.dto.ChessBoardDto;
import chess.dto.PieceDto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

public class BoardDao implements BoardRepository {
    @Override
    public void add(final ChessBoardDto chessBoardDto) {
        final String chessBoardQuery = "INSERT INTO chess_board (position, type, turn) VALUES (?, ?, ?)";
        try (final Connection connection = CommonDao.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(chessBoardQuery)) {
            final Set<PieceDto> pieces = chessBoardDto.pieces();
            pieces.forEach(pieceDto -> addBatch(preparedStatement, chessBoardDto, pieceDto));
            preparedStatement.executeBatch();
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
        try (final var connection = CommonDao.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, data);
            final ResultSet resultSet = preparedStatement.executeQuery();
            validateResultSet(resultSet);
            final String position = resultSet.getString("position");
            final String type = resultSet.getString("type");
            return PieceDto.from(position, type);
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ChessBoardDto findAll() {
        final String query = "SELECT * FROM chess_board;";
        try (final Connection connection = CommonDao.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            final Set<PieceDto> pieces = new HashSet<>();
            final ResultSet resultSet = preparedStatement.executeQuery();
            return getChessBoardDto(resultSet, pieces);
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int count() {
        final String query = "SELECT COUNT(*) AS `count` FROM chess_board;";
        try (final Connection connection = CommonDao.getConnection();
             final Statement statement = connection.createStatement()) {
            final ResultSet resultSet = statement.executeQuery(query);
            validateResultSet(resultSet);
            return resultSet.getInt("count");
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteAll() {
        final String boardQuery = "DELETE FROM chess_board;";
        try (final Connection connection = CommonDao.getConnection();
             final Statement statement = connection.createStatement()) {
            statement.execute(boardQuery);
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void validateResultSet(final ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            throw new RuntimeException("데이터가 존재하지 않습니다.");
        }
    }

    private void addBatch(final PreparedStatement preparedStatement,
                          final ChessBoardDto chessBoardDto,
                          final PieceDto pieceDto) {
        try {
            preparedStatement.setString(1, pieceDto.position());
            preparedStatement.setString(2, pieceDto.type());
            preparedStatement.setString(3, chessBoardDto.turn());
            preparedStatement.addBatch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String getTurn(final ResultSet resultSet, String turn) throws SQLException {
        if (turn == null) {
            turn = resultSet.getString("turn");
        }
        return turn;
    }

    private ChessBoardDto getChessBoardDto(final ResultSet resultSet,
                                           final Set<PieceDto> pieces) throws SQLException {
        String turn = null;
        while (resultSet.next()) {
            turn = getTurn(resultSet, turn);
            final String position = resultSet.getString("position");
            final String type = resultSet.getString("type");
            pieces.add(PieceDto.from(position, type));
        }
        return ChessBoardDto.from(pieces, turn);
    }
}
