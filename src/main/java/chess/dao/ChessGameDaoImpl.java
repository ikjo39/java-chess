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

public class ChessGameDaoImpl implements ChessGameDao {
    @Override
    public void addAll(final ChessBoardDto chessBoardDto) {
        final String chessBoardQuery = "INSERT INTO chess_game (position, piece_type, turn) VALUES (?, ?, ?)";
        try (final Connection connection = CommonDao.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(chessBoardQuery)) {
            final Set<PieceDto> pieces = chessBoardDto.pieces();
            pieces.forEach(pieceDto -> addBatch(preparedStatement, chessBoardDto, pieceDto));
            preparedStatement.executeBatch();
        } catch (final SQLException e) {
            throw new RuntimeException("데이터 삽입 과정에서 오류가 생겨 해당 명령을 수행할 수 없습니다.");
        }
    }

    @Override
    public ChessBoardDto findAll() {
        final String query = "SELECT * FROM chess_game;";
        try (final Connection connection = CommonDao.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            final Set<PieceDto> pieces = new HashSet<>();
            final ResultSet resultSet = preparedStatement.executeQuery();
            return getChessBoardDto(resultSet, pieces);
        } catch (final SQLException e) {
            throw new RuntimeException("데이터 조회 과정에서 오류가 생겨 해당 명령을 수행할 수 없습니다.");
        }
    }

    @Override
    public boolean hasAnyData() {
        final String query = "SELECT CASE WHEN COUNT(*) > 0 THEN TRUE ELSE FALSE END AS has_any_data FROM chess_game;";
        try (final Connection connection = CommonDao.getConnection();
             final Statement statement = connection.createStatement()) {
            final ResultSet resultSet = statement.executeQuery(query);
            validateResultSet(resultSet);
            return resultSet.getBoolean("has_any_data");
        } catch (final SQLException e) {
            throw new RuntimeException("데이터 존재 여부 조회 과정에서 오류가 생겨 해당 명령을 수행할 수 없습니다.");
        }
    }

    @Override
    public void deleteAll() {
        final String boardQuery = "DELETE FROM chess_game;";
        try (final Connection connection = CommonDao.getConnection();
             final Statement statement = connection.createStatement()) {
            statement.execute(boardQuery);
        } catch (final SQLException e) {
            throw new RuntimeException("데이터 삭제 과정에서 오류가 생겨 해당 명령을 수행할 수 없습니다.");
        }
    }

    private void validateResultSet(final ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            throw new RuntimeException("데이터가 존재하지 않아 데이터를 불러올 수 없습니다.");
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
            throw new RuntimeException("데이터 조회 시 문제가 생겨 데이터를 불러올 수 없습니다.");
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
            final String piece_type = resultSet.getString("piece_type");
            pieces.add(PieceDto.from(position, piece_type));
        }
        return ChessBoardDto.from(pieces, turn);
    }
}
