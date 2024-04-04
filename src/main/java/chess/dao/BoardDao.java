package chess.dao;

import chess.dto.ChessBoardDto;

public interface BoardDao {
    void addAll(final ChessBoardDto chessBoardDto);

    ChessBoardDto findAll();

    int count();

    void deleteAll();
}
