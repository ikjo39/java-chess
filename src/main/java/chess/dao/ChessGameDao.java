package chess.dao;

import chess.dto.ChessBoardDto;

public interface ChessGameDao {
    void addAll(final ChessBoardDto chessBoardDto);

    ChessBoardDto findAll();

    int count();

    void deleteAll();
}
